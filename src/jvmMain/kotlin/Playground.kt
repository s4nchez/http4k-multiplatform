import org.http4k.client.JavaHttpClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    val app = { request: Request -> Response(Status.OK).body("Hello, world!") }

    val server = app.asServer(SunHttp(9000))

    server.start()

    val response = JavaHttpClient()(Request(Method.GET, "http://localhost:9000"))

    println(response)

    server.stop()
}