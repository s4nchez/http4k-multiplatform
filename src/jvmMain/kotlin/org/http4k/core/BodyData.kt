package org.http4k.core

import java.io.InputStream
import java.nio.ByteBuffer

actual object BodyData {
    actual fun of(array: ByteArray): DataInMemory = DataInMemory(ByteBuffer.wrap(array))

    actual fun of(string: String): DataInMemory = DataInMemory(string.asByteBuffer())
}

actual data class DataInMemory(val payload: ByteBuffer) {
    actual fun asStream(): DataStream {
        return DataStream(payload.array().inputStream(payload.position(), size()))
    }

    actual fun asString(): String = String(payload.array())

    actual fun size(): Int = payload.limit() - payload.position()

}

actual data class DataStream(val inputStream: InputStream) : Closeable {
    actual fun consumeAll(): DataInMemory = inputStream.use { BodyData.of(it.readBytes()) }

    override fun close() {
        inputStream.close()
    }
}

private fun String.asByteBuffer(): ByteBuffer = toByteArray().asByteBuffer()

private fun ByteArray.asByteBuffer(): ByteBuffer = ByteBuffer.wrap(this)