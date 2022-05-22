package com.thana.kotlin_ide

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class IdeRepository {

    private val httpClientConfig: HttpClientConfig<*>.() -> Unit = {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    expectSuccess = false
                }
            )
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    @Throws(Exception::class)
    suspend fun getOutput(script: String): IdeResult<Any> {
        var ideResult: IdeResult<Any>
        val client = HttpClient(httpClientConfig)
        try {
            client.responsePipeline.intercept(HttpResponsePipeline.Transform) { (_, body) ->
                ideResult = when (context.response.status) {
                    HttpStatusCode.OK -> IdeResult.success(body)
                    else -> IdeResult.error(1, "Something went wrong..")
                }
            }

            ideResult = IdeResult.success(client.post(BASE_URL) {
                contentType(ContentType.Application.Json)
                setBody(IdeRequest().copy(script = script))
            }.body<IdeResponse>())

        } catch (exception: Exception) {
            ideResult = IdeResult.error(
                2,
                "Something went wrong..\n Please check your internet connection then retry $exception"
            )
        }
        return ideResult
    }

    companion object {
        const val BASE_URL = "https://api.jdoodle.com/execute"
    }
}