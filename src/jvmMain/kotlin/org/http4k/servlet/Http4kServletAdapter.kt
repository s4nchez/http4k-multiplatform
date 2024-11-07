package org.http4k.servlet

import kotlinx.io.asInputStream
import org.http4k.core.*
import java.util.Enumeration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Adapts between the Servlet and http4k APIs
 */
class Http4kServletAdapter(private val handler: HttpHandler) {
    fun handle(req: HttpServletRequest, resp: HttpServletResponse) = handler(req.asHttp4kRequest()).transferTo(resp)
}

@Suppress("DEPRECATION")
fun Response.transferTo(destination: HttpServletResponse) {
    destination.setStatus(status.code, status.description)
    headers.forEach { (key, value) -> destination.addHeader(key, value) }
    body.stream.asInputStream().use { input -> destination.outputStream.use { output -> input.copyTo(output) } }
}

fun HttpServletRequest.asHttp4kRequest() =
    Request(Method.valueOf(method), Uri.of(requestURI + queryString.toQueryString()))
        .body(BodyMode.Stream(inputStream).stream, getHeader("Content-Length").safeLong()).headers(headerParameters())
        .source(RequestSource(remoteAddr, remotePort, scheme))

private fun HttpServletRequest.headerParameters() =
    headerNames.asSequence().fold(listOf()) { a: Parameters, b: String -> a.plus(getHeaders(b).asPairs(b)) }

private fun Enumeration<String>.asPairs(key: String) = asSequence().map { key to it }.toList()

private fun String?.toQueryString(): String = if (!isNullOrEmpty()) "?$this" else ""
