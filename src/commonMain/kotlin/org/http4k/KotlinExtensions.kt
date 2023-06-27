package org.http4k


expect object BasicEncoders {
    fun urlEncode(value: String): String
    fun urlDecode(value: String): String
    fun base64Encode(value: String): String
    fun base64Decode(value: String): String
}


fun String.quoted() = "\"${replace("\"", "\\\"")}\""

fun String.unquoted(): String =
    replaceFirst("^\"".toRegex(), "").replaceFirst("\"$".toRegex(), "").replace("\\\"", "\"")

fun StringBuilder.appendIfNotBlank(valueToCheck: String, vararg toAppend: String): StringBuilder =
    appendIf({ valueToCheck.isNotBlank() }, *toAppend)

fun StringBuilder.appendIfNotEmpty(valueToCheck: List<Any>, vararg toAppend: String): StringBuilder =
    appendIf({ valueToCheck.isNotEmpty() }, *toAppend)

fun StringBuilder.appendIfPresent(valueToCheck: Any?, vararg toAppend: String): StringBuilder =
    appendIf({ valueToCheck != null }, *toAppend)

fun StringBuilder.appendIf(condition: () -> Boolean, vararg toAppend: String): StringBuilder = apply {
    if (condition()) toAppend.forEach { append(it) }
}

fun String.base64Decoded() = BasicEncoders.base64Decode(this)

fun String.base64Encode(): String = BasicEncoders.urlEncode(this)

fun String.urlEncoded(): String = BasicEncoders.urlEncode(this)

fun String.urlDecoded(): String = BasicEncoders.urlDecode(this)
