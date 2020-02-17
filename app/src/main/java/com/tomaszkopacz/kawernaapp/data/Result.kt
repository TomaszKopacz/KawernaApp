package com.tomaszkopacz.kawernaapp.data

sealed class Result<out R> {
    class Success<out T>(val data: T) : Result<T>()
    class Failure(val message: Message) : Result<Nothing>()
}