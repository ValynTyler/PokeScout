package com.example.result

sealed class Result<T, E> {
    data class Ok<T, E>(val value: T): Result<T, E>()
    data class Err<T, E>(val error: E): Result<T, E>()
}

fun <T, E> Result<T, E>.ok(): T? {
    return when (this) {
        is Result.Ok -> this.value
        is Result.Err -> null
    }
}