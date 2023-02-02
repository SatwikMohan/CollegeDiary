package com.gigawattstechnology.collegediary.CustomClasses

import com.google.gson.annotations.SerializedName

data class DataRegisterBody(
    @SerializedName("dataSource")
    val dataSource:String,
    @SerializedName("database")
    val database:String,
    @SerializedName("collection")
    val collection:String,
    @SerializedName("document")
    val document:DataRegisterDoc
)
