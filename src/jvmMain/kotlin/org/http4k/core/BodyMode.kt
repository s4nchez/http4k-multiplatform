package org.http4k.core

import kotlinx.io.Buffer
import kotlinx.io.asSource
import kotlinx.io.buffered
import java.io.InputStream

/**
 * BodyMode represents a choice between working lazily with streams or eagerly storing the body contents in memory.
 *
 * This choice comes with the following trade-offs:
 *
 * `Memory` does not require any special treatment. However, you need to be confident that the sum of all "in-flight"
 * body bytes does not exceed your JVM heap. Otherwise, you'll run into `OutOfMemoryException's.
 *
 * `Stream`, on the other hand, allows you to handle payloads of any size, but you'll need to make sure that:
 * 1. You're consuming it only once and at the right place (harder to add "debugging" filters too).
 * 2. It's _always_ consumed, or `close()` is called appropriately.
 */
sealed class BodyMode : (InputStream) -> Body {
    object Memory : BodyMode() {
        override fun invoke(stream: InputStream): Body =
            stream.use { Body(Buffer().apply { write(stream.readBytes()) }) }
    }
    object Stream : BodyMode() {
        override fun invoke(stream: InputStream): Body = StreamBody(stream.asSource().buffered())
    }
}

