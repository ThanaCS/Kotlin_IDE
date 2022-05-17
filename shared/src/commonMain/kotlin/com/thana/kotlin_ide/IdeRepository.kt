package com.thana.kotlin_ide

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.parsing.*
import kotlinx.serialization.json.Json

class IdeRepository {
    private val client = HttpClient {
        install(JsonFeature) {
            val json = Json {
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
                expectSuccess = false
            }
            serializer = KotlinxSerializer(json)
        }
    }

    @Throws(Exception::class)
    suspend fun getResponse(script: String): Result<Any> {
        return IdeRepository().fetchData(script)
    }

    private suspend fun fetchData(script: String): Result<Any> {

        var result: Result<Any> = Result.loading()

        try {
            client.responsePipeline.intercept(HttpResponsePipeline.Transform) { (_, body) ->
                result = when (context.response.status) {
                    HttpStatusCode.OK -> Result.success(body)
                    else -> Result.error(1, "Something went wrong..")
                }
            }
            client.post<Response>(BASE_URL) {
                contentType(ContentType.Application.Json)
                body = Request().copy(script = script)
            }
        } catch (exception: Exception) {
            result = Result.error(
                2,
                "Something went wrong..\n Please check your internet connection then retry"
            )
        }
        return result
    }

    companion object {
        const val BASE_URL = "https://api.jdoodle.com/execute"
    }
}