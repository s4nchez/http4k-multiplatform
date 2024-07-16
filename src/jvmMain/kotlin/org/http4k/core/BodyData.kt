@file:OptIn(ExperimentalStdlibApi::class)

package org.http4k.core

import java.io.InputStream
import java.nio.ByteBuffer

actual data class DataInMemory(val payload: ByteBuffer) {
    actual fun asStream(): DataStream {
        return DataStream(payload.array().inputStream(payload.position(), size()))
    }

    actual fun asString(): String = String(payload.array())

    actual fun size(): Int = payload.limit() - payload.position()

    actual constructor(value: String) : this(value.asByteBuffer())

    actual constructor(value: ByteArray): this(ByteBuffer.wrap(value))

}

actual data class DataStream(val inputStream: InputStream) : AutoCloseable  {
    actual fun consumeAll(): DataInMemory = inputStream.use { DataInMemory(it.readBytes()) }

    override fun close() {
        inputStream.close()
    }
}

private fun String.asByteBuffer(): ByteBuffer = toByteArray().asByteBuffer()

private fun ByteArray.asByteBuffer(): ByteBuffer = ByteBuffer.wrap(this)