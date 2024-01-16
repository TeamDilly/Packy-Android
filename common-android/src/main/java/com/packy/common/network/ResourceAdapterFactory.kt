package com.packy.common.network

import com.packy.lib.utils.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResourceAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }
        if (returnType !is ParameterizedType) {
            throw IllegalStateException("Resource must be parameterized")
        }
        val responseType = getParameterUpperBound(0, returnType)
        return ResourceCallAdapter<Any>(responseType)
    }
}