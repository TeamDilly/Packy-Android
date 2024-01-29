package com.packy.lib.utils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private const val HTTP_SUCCESS_RANGE_START = 200
private const val HTTP_SUCCESS_RANGE_END = 299
const val HTTP_CLIENT_ERROR_RANGE_START = 400
const val HTTP_CLIENT_ERROR_RANGE_END = 409
const val HTTP_SERVER_ERROR_RANGE_START = 500
const val HTTP_SERVER_ERROR_RANGE_END = 509

const val EMPTY_CODE = "EMPTY_CODE"
const val EMPTY_MESSAGE = "EMPTY_MESSAGE"

fun HttpStatusCode.isSuccess(): Boolean =
    value in (HTTP_SUCCESS_RANGE_START until HTTP_SUCCESS_RANGE_END)

fun HttpStatusCode.isClientError(): Boolean =
    value in (HTTP_CLIENT_ERROR_RANGE_START until HTTP_CLIENT_ERROR_RANGE_END)

fun HttpStatusCode.isServerError(): Boolean =
    value in (HTTP_SERVER_ERROR_RANGE_START until HTTP_SERVER_ERROR_RANGE_END)



suspend inline fun<reified T> HttpResponse.toResource(): Resource<T> {
    try {
        if (status.isSuccess()) {
            val kJson = Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            }
            val json = body<String>()
            val jsonElement = Json.parseToJsonElement(json)
            val message = jsonElement.jsonObject["message"]?.jsonPrimitive?.content
                ?: EMPTY_MESSAGE
            val code =
                jsonElement.jsonObject["code"]?.jsonPrimitive?.content ?: EMPTY_CODE
            val data = jsonElement.jsonObject["data"]?.let {
                kJson.decodeFromJsonElement<T>(it)
            }

            return if (data != null) {
                Resource.Success(data = data, message = message, code = code)
            } else {
                Resource.NullResult<T>(message = message, code = code)
            }
        } else if (status.isClientError()) {
            val json = body<String>()
            val jsonElement = Json.parseToJsonElement(json)
            val message = jsonElement.jsonObject["message"]?.jsonPrimitive?.content
                ?: EMPTY_MESSAGE
            val code =
                jsonElement.jsonObject["code"]?.jsonPrimitive?.content ?: EMPTY_CODE
            return Resource.ApiError(data = null, message = message, code = code)
        } else {
            return Resource.NetworkError(throwable = Throwable("Network Error"))
        }
    } catch (e: Exception) {
        println("LOGEE $e")
        return Resource.NetworkError(throwable = e)
    }
}