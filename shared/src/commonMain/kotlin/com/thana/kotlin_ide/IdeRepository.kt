package com.thana.kotlin_ide

import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
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
    suspend fun getResponse(script: String): IdeResult<Any> {
        return IdeRepository().fetchData(script)
    }

    private suspend fun fetchData(script: String): IdeResult<Any> {

        var ideResult: IdeResult<Any> = IdeResult.loading()

        try {
            client.responsePipeline.intercept(HttpResponsePipeline.Transform) { (_, body) ->
                ideResult = when (context.response.status) {
                    HttpStatusCode.OK -> IdeResult.success(body)
                    else -> IdeResult.error(1, "Something went wrong..")
                }
            }
            client.post<IdeResponse>(BASE_URL) {
                contentType(ContentType.Application.Json)
                body = IdeRequest().copy(script = script)
            }
        } catch (exception: Exception) {
            ideResult = IdeResult.error(
                2,
                "Something went wrong..\n Please check your internet connection then retry"
            )
        }
        return ideResult
    }

    companion object {
        const val BASE_URL = "https://api.jdoodle.com/execute"
    }
}