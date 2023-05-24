package com.example.restaurant.domain.models

sealed class Result<T> {
    class Loading<T> : Result<T>()
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val throwable: Throwable): Result<T>()
}

