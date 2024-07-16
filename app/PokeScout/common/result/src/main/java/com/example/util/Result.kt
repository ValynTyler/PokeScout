package com.example.util

sealed class Result<T, E> {
    data class Ok<T, E>(val value: T): Result<T, E>()
    data class Err<T, E>(val error: E): Result<T, E>()
}