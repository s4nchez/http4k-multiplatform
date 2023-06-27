package org.http4k.core

actual data class DataStream(val value: String) : Closeable {
    actual fun consumeAll(): DataInMemory = DataInMemory(value)

    override fun close() {
    }
}

actual data class DataInMemory actual constructor(val value:String)  {
    actual fun asStream(): DataStream = DataStream(value)

    actual fun asString(): String = value

    actual fun size(): Int = value.length

    actual constructor(value: ByteArray) : this("") {
        TODO("Not yet implemented")
    }
}