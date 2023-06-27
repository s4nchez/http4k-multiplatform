package org.http4k.core

import java.io.InputStream
import java.nio.ByteBuffer

actual object BodyData {
    actual fun of(array: ByteArray): DataInMemory = ByteBufferDataInMemory(ByteBuffer.wrap(array))

    actual fun of(string: String): DataInMemory = ByteBufferDataInMemory(string.asByteBuffer())
}

data class ByteBufferDataInMemory(val payload: ByteBuffer) : DataInMemory {
    override fun asStream(): DataStream {
        return InputStreamDataStream(payload.array().inputStream(payload.position(), size()))
    }

    override fun asString(): String = String(payload.array())

    override fun size(): Int = payload.limit() - payload.position()

}

data class InputStreamDataStream(val inputStream: InputStream) : DataStream {
    override fun consumeAll(): DataInMemory = inputStream.use { BodyData.of(it.readBytes()) }

    override fun close() {
        inputStream.close()
    }
}

private fun String.asByteBuffer(): ByteBuffer = toByteArray().asByteBuffer()

private fun ByteArray.asByteBuffer(): ByteBuffer = ByteBuffer.wrap(this)