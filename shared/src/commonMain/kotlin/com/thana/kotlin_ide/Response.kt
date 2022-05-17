package com.thana.kotlin_ide

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val output: String? = null,
    val statusCode: String? = null,
    val memory: String? = null,
    val cpuTime: String? = null,
    val error: String? = null,
)

data class Result<out T>(
    val data: T? = null,
    val message: String? = null,
    val error: Error? = null,
    val state: State? = null,
    val errorResponse: T? = null
) {

    enum class State {
        LOADING,
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> loading(): Result<T> {
            return Result(data = null, state = State.LOADING)
        }

        fun <T> success(data: T?): Result<T> {
            return Result(data = data, state = State.SUCCESS)
        }

        fun <T> error(code: Int? = null, message: String?): Result<T> {
            return Result(error = Error(code, message), state = State.ERROR)
        }

    }
}

data class Error(
    val code: Int?,
    val message: String?
)
