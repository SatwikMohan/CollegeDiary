package com.gigawattstechnology.collegediary.CustomClasses

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private var retrofit: Retrofit?=null
    val baseUrl="https://data.mongodb-api.com/app/data-ebzua/endpoint/data/v1/"
    fun getRetrofit():Retrofit?{
        if (retrofit==null){
            retrofit=Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl).build()
        }
        return retrofit
    }
}