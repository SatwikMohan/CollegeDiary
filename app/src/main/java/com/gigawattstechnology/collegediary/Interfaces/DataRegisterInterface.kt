package com.gigawattstechnology.collegediary.Interfaces

import com.gigawattstechnology.collegediary.CustomClasses.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DataRegisterInterface {
    @Headers(
        "api-key:u3LxylSwVYOoOclbe6YAq2pdsabSCIoOlbjcCL7b91SMJ8KkWaraQ8kHZ5GPuOiX",
        "Content-Type:application/json"
    )
    @POST("action/insertOne")
    fun insertOne(@Body body:DataRegisterBody): Call<RegisterResponseBody>
    @Headers(
        "api-key:u3LxylSwVYOoOclbe6YAq2pdsabSCIoOlbjcCL7b91SMJ8KkWaraQ8kHZ5GPuOiX",
        "Content-Type:application/json"
    )
    @POST("action/findOne")
    fun findOne(@Body body:FindDataBody): Call<JsonObject>
    @Headers(
        "api-key:u3LxylSwVYOoOclbe6YAq2pdsabSCIoOlbjcCL7b91SMJ8KkWaraQ8kHZ5GPuOiX",
        "Content-Type:application/json"
    )
    @POST("action/find")
    fun findAll(@Body body:AllDataBody): Call<JsonObject>
}