package org.http4k.core

actual object BodyData {
    actual fun of(array: ByteArray): DataInMemory {
        TODO("Not yet implemented")
    }

    actual fun of(string: String): DataInMemory {
        TODO("Not yet implemented")
    }
}

actual class DataStream : Closeable {
    actual fun consumeAll(): DataInMemory {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}

actual class DataInMemory {
    actual fun asStream(): DataStream {
        TODO("Not yet implemented")
    }

    actual fun asString(): String {
        TODO("Not yet implemented")
    }

    actual fun size(): Int {
        TODO("Not yet implemented")
    }
}