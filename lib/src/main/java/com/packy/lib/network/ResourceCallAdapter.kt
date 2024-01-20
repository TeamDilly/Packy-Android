package com.packy.lib.network

import com.packy.lib.utils.Resource
import com.packy.lib.utils.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResourceCallAdapter<T>(
    private val type: Type,
) : CallAdapter<ResponseBody<T>, Call<Resource<T>>> {
    override fun responseType(): Type = type
    override fun adapt(call: Call<ResponseBody<T>>): Call<Resource<T>> = ResourceCall(call, type)
}