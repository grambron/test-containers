package com.badikova.common.exception

class NotEnoughStocksException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}