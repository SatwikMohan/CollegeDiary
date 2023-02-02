package com.gigawattstechnology.collegediary.CustomClasses

import com.google.gson.annotations.SerializedName

data class RegisterResponseBody(
    @SerializedName("insertedId")
    val insertID:String?
)
