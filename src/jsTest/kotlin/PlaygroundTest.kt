import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlin.test.Test

class PlaygroundTest {
    @Test
    fun call_handler() {
        val server = { request: Request -> Response(Status.OK).body("Hello, world!") }

        val response = server(Request(Method.GET, "http://localhost:9000"))

        println(response)
    }
}
