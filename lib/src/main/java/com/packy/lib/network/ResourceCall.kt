package com.packy.lib.network

import com.packy.lib.utils.Resource
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResourceCall<T>(
    private val callDelegate: Call<T>,
) : Call<Resource<T>> {

    override fun enqueue(callback: Callback<Resource<T>>) =
        callDelegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                try {
                    val json = Json { encodeDefaults = true }

                    when (response.code()) {
                        in HTTP_SUCCESS_RANGE_START..HTTP_SUCCESS_RANGE_END -> {
                            response.body()?.let {
                                println(it)
                                callback.onResponse(
                                    this@ResourceCall,
                                    Response.success(Resource.NullResult())
                                )
                            } ?: callback.onResponse(
                                this@ResourceCall,
                                Response.success(Resource.NullResult())
                            )
                        }

                        in HTTP_CLIENT_ERROR_RANGE_START..HTTP_CLIENT_ERROR_RANGE_END -> {
                            response.errorBody()?.string()?.let {
                                val jsonElement = Json.parseToJsonElement(it)
                                val resource = Resource.ApiError(
                                    jsonElement.jsonObject["data"]?.let {
                                        val data: Any? = json.decodeFromJsonElement(it)
                                        @Suppress("UNCHECKED_CAST")
                                        data as T
                                    },
                                    jsonElement.jsonObject["message"]?.jsonPrimitive?.content
                                        ?: "",
                                    jsonElement.jsonObject["code"]?.jsonPrimitive?.content
                                        ?: ""
                                )
                                callback.onResponse(
                                    this@ResourceCall,
                                    Response.success(resource)
                                )
                            } ?: callback.onResponse(
                                this@ResourceCall,
                                Response.success(Resource.NullResult())
                            )
                        }
                    }
                } catch (e: Exception) {
                    callback.onResponse(
                        this@ResourceCall,
                        Response.success(Resource.NetworkError(e))
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(this@ResourceCall, Response.success(Resource.NetworkError(t)))
                call.cancel()
            }
        })

    override fun clone(): Call<Resource<T>> = ResourceCall(callDelegate.clone())

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
    }
}