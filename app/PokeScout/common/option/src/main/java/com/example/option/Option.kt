package com.example.option

sealed class Option<T> {
    data class Some<T>(val value: T): Option<T>()
    data class None<T>(val none: Unit): Option<T>()
}