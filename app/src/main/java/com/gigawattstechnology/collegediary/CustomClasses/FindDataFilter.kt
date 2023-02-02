package com.gigawattstechnology.collegediary.CustomClasses

import com.google.gson.annotations.SerializedName

data class FindDataFilter(
    @SerializedName("email")
    val email:String
)
