package com.gigawattstechnology.collegediary.CustomClasses

import com.google.gson.annotations.SerializedName

data class InterestFilter(
    @SerializedName("interests")
    val interests:ArrayList<String>
)
