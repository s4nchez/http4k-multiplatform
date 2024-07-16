package org.http4k.core

import java.lang.AutoCloseable


actual class DataInMemory(private val payload:Payload) {

    actual fun asStream(): DataStream = DataStream(payload)

    actual fun asString(): String = payload.value

    actual fun size(): Int = payload.value.length

    actual constructor(value: ByteArray): this(Payload(value.decodeToString()))
    actual constructor(value: String) : this(Payload(value))
}

actual data class DataStream(val payload: Payload) : AutoCloseable {
    actual fun consumeAll(): DataInMemory = DataInMemory(payload)

    override fun close() {

    }

}

data class Payload(val value:String)