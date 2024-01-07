package com.packy.data.remote.example

import com.packy.data.model.example.ExampleResponse
import retrofit2.http.GET

interface ExampleService {
    @GET("api/users/")
    suspend fun getExample(): ExampleResponse
}