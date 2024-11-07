package org.http4k

import com.jetbrains.handson.http.*
import kotlinx.cinterop.*
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import platform.posix.size_t

@OptIn(ExperimentalForeignApi::class)
class LibCurl : HttpHandler {

    override fun invoke(request: Request): Response {
        val curl = curl_easy_init()
        if (curl != null) {
            val responseBuffer = mutableListOf<Byte>()
            val responseRef = StableRef.create(responseBuffer)

            curl_easy_setopt(curl, CURLOPT_URL, request.uri.toString())
            curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, staticCFunction(::writeCallback))
            curl_easy_setopt(curl, CURLOPT_WRITEDATA, responseRef.asCPointer())

            val res = curl_easy_perform(curl)
            if (res != CURLE_OK) {
                println("curl_easy_perform() failed ${curl_easy_strerror(res)?.toKString()}")
            }

            curl_easy_cleanup(curl)
            responseRef.dispose()

            val responseString = responseBuffer.toByteArray().toKString()
            return Response(Status.OK).body(responseString)
        }
        return Response(Status.INTERNAL_SERVER_ERROR)
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun writeCallback(buffer: CPointer<ByteVar>?, size: size_t, nmemb: size_t, userData: COpaquePointer?): size_t {
    val realSize = size * nmemb
    val response = userData!!.asStableRef<MutableList<Byte>>().get()
    buffer?.let {
        for (i in 0 until realSize.toInt()) {
            response.add(it[i])
        }
    }
    return realSize
}