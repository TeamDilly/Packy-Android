package com.packy.common.network

import android.util.Log
import com.packy.lib.utils.Resource
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResourceCall<T> constructor(
    private val callDelegate: Call<T>
) : Call<Resource<T>> {

    override fun enqueue(callback: Callback<Resource<T>>) =
        callDelegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.body()?.let {
                    Log.d("LOGEE", "onResponse: $response")
                } ?: callback.onResponse(
                    this@ResourceCall,
                    Response.success(Resource.NullResult())
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d("LOGEE", "onFailure: $call")
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
}