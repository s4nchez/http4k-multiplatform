@file:OptIn(ExperimentalStdlibApi::class)

package org.http4k.core

actual class DataStream : AutoCloseable {
    actual fun consumeAll(): DataInMemory {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}

actual class DataInMemory actual constructor(value: String) {
    actual fun asStream(): DataStream {
        TODO("Not yet implemented")
    }

    actual fun asString(): String {
        TODO("Not yet implemented")
    }

    actual fun size(): Int {
        TODO("Not yet implemented")
    }

    actual constructor(value: ByteArray) : this("") {
        TODO("Not yet implemented")
    }
}