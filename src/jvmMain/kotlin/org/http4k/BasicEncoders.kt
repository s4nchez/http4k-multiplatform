package org.http4k

import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*

actual object BasicEncoders {
    actual fun urlEncode(value: String): String = URLEncoder.encode(value, "utf-8")

    actual fun urlDecode(value: String): String = URLDecoder.decode(value, "utf-8")

    actual fun base64Encode(value: String): String = Base64.getEncoder().encodeToString(value.toByteArray())

    actual fun base64Decode(value: String): String = Base64.getDecoder().decode(value).decodeToString()

}

