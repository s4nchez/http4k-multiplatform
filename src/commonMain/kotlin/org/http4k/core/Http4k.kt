package org.http4k.core


typealias HttpHandler = (request: Request) -> Response

typealias Filter = (HttpHandler) -> HttpHandler

fun Filter(actual: Filter): Filter = actual

val Filter.NoOp: Filter get() = Filter { next -> { next(it) } }

fun Filter.then(next: Filter): Filter = Filter { this(next(it)) }

fun Filter.then(next: HttpHandler): HttpHandler = this(next).let { http -> { http(it) } }

