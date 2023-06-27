package org.http4k.core

import org.http4k.routing.RoutingHttpHandler

typealias HttpHandler = (request: Request) -> Response

typealias Filter = (HttpHandler) -> HttpHandler

fun Filter(actual: Filter): Filter = actual

val Filter.NoOp: Filter get() = Filter { next -> { next(it) } }

fun Filter.then(next: Filter): Filter = Filter { this(next(it)) }

fun Filter.then(next: HttpHandler): HttpHandler = this(next).let { http -> { http(it) } }

fun Filter.then(routingHttpHandler: RoutingHttpHandler): RoutingHttpHandler = routingHttpHandler.withFilter(this)
