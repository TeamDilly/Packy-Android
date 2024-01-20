package com.packy.lib.network

import com.packy.lib.utils.Resource
import com.packy.lib.utils.ResponseBody
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class ResourceCall<T>(
    private val callDelegate: Call<ResponseBody<T>>,
    private val responseType: Type
) : Call<Resource<T>> {

    override fun enqueue(callback: Callback<Resource<T>>) =
        callDelegate.enqueue(object : Callback<ResponseBody<T>> {
            override fun onResponse(
                call: Call<ResponseBody<T>>,
                response: Response<ResponseBody<T>>
            ) {
                try {
                    when (response.code()) {
                        in HTTP_SUCCESS_RANGE_START..HTTP_SUCCESS_RANGE_END -> {
                            val body = response.body().toString()
                            val jsonElement = Json.parseToJsonElement(body)
                            val message = jsonElement.jsonObject["message"]?.jsonPrimitive?.content
                                ?: EMPTY_MESSAGE
                            val code =
                                jsonElement.jsonObject["code"]?.jsonPrimitive?.content ?: EMPTY_CODE
                            @Suppress("UNCHECKED_CAST")
                            val data = jsonElement.jsonObject["data"]?.let { it as? T }

                            if (data != null) {
                                callback.onResponse(
                                    this@ResourceCall,
                                    Response.success(
                                        Resource.Success(
                                            code = code,
                                            message = message,
                                            data = data
                                        )
                                    )
                                )
                            } else {
                                callback.onResponse(
                                    this@ResourceCall,
                                    Response.success(Resource.NullResult())
                                )
                            }
                        }

                        in HTTP_CLIENT_ERROR_RANGE_START..HTTP_CLIENT_ERROR_RANGE_END -> {
                            val body = response.errorBody()?.string()
                            if (body != null) {
                                val jsonElement = Json.parseToJsonElement(body)
                                val message =
                                    jsonElement.jsonObject["message"]?.jsonPrimitive?.content
                                        ?: EMPTY_MESSAGE
                                val code =
                                    jsonElement.jsonObject["code"]?.jsonPrimitive?.content
                                        ?: EMPTY_CODE
                                @Suppress("UNCHECKED_CAST")
                                val data = jsonElement.jsonObject["data"]?.let { it as? T }

                                callback.onResponse(
                                    this@ResourceCall,
                                    Response.success(
                                        Resource.ApiError(
                                            code = code,
                                            message = message,
                                            data = data
                                        )
                                    )
                                )
                            } else {
                                callback.onResponse(
                                    this@ResourceCall,
                                    Response.success(Resource.NullResult())
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    callback.onResponse(
                        this@ResourceCall,
                        Response.success(Resource.NetworkError(e))
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody<T>>, t: Throwable) {
                callback.onResponse(
                    this@ResourceCall,
                    Response.success(Resource.NetworkError(t))
                )
                call.cancel()
            }
        })

    override fun clone(): Call<Resource<T>> =
        ResourceCall(callDelegate.clone(), responseType)

    override fun execute(): Response<Resource<T>> =
        throw UnsupportedOperationException("ResourceCall does not support execute.")

    override fun isExecuted(): Boolean = callDelegate.isExecuted

    override fun cancel() = callDelegate.cancel()

    override fun isCanceled(): Boolean = callDelegate.isCanceled

    override fun request(): Request = callDelegate.request()

    override fun timeout(): Timeout = callDelegate.timeout()

    companion object {
        const val HTTP_SUCCESS_RANGE_START = 200
        const val HTTP_SUCCESS_RANGE_END = 299
        const val HTTP_CLIENT_ERROR_RANGE_START = 400
        const val HTTP_CLIENT_ERROR_RANGE_END = 409

        const val EMPTY_CODE = "EMPTY_CODE"
        const val EMPTY_MESSAGE = "EMPTY_MESSAGE"
    }
}