package com.gigawattstechnology.collegediary.CustomClasses

import com.google.gson.annotations.SerializedName

data class AllDataBody(
    @SerializedName("dataSource")
    val dataSource:String,
    @SerializedName("database")
    val database:String,
    @SerializedName("collection")
    val collection:String,
    @SerializedName("filter")
    val filter:AllDataFilter
)
