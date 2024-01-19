package com.packy.lib.network

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
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return 타입은 ApiResult<Foo> 또는 ApiResult<out Foo>로 정의되어야 합니다." }

        val wrapperType = getParameterUpperBound(0, returnType)
        if (getRawType(wrapperType) != Resource::class.java) return null
        check(wrapperType is ParameterizedType) { "Return 타입은 Resource<ResponseBody>로 정의되어야 합니다." }

        val bodyType = getParameterUpperBound(0, wrapperType)
        return ResourceCallAdapter<Any>(bodyType)
    }
}