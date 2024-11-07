@file:Suppress("UNUSED_PARAMETER")

import org.http4k.LibCurl
import org.http4k.core.Method
import org.http4k.core.Request

fun main(args: Array<String>) {
    val client = LibCurl()
    client(Request(Method.GET, "https://httpbin.org/uuid")).also(::println)
}