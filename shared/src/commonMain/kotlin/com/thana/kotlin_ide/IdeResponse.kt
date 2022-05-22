package com.thana.kotlin_ide

import kotlinx.serialization.Serializable

@Serializable
data class IdeResponse(
    val output: String? = null,
    val statusCode: String? = null,
    val memory: String? = null,
    val cpuTime: String? = null,
    val error: String? = null,
)

@Serializable
data class IdeResult<out T>(
    val data: T? = null,
    val message: String? = null,
    val error: Error? = null,
    val state: State? = null) {
    @Serializable
    enum class State {
        LOADING,
        SUCCESS,
        ERROR
    }

    @Serializable
    companion object {
        fun <T> loading(): IdeResult<T> {
            return IdeResult(data = null, state = State.LOADING)
        }

        fun <T> success(data: T?): IdeResult<T> {
            return IdeResult(data = data, state = State.SUCCESS)
        }

        fun <T> error(code: Int? = null, message: String?): IdeResult<T> {
            return IdeResult(error = Error(code, message), state = State.ERROR)
        }

    }
}

@Serializable
data class Error(
    val code: Int?,
    val message: String?
)
