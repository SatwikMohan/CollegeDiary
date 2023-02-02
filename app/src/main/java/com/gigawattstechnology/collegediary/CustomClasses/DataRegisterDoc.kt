package com.gigawattstechnology.collegediary.CustomClasses

import com.google.gson.annotations.SerializedName

data class DataRegisterDoc(
    @SerializedName("email")
    val email:String?,
    @SerializedName("password")
    val password:String?,
    @SerializedName("picURL")
    val picURL:String?,
    @SerializedName("BirthDate")
    val BirthDate:String?,
    @SerializedName("interests")
    val interests:ArrayList<String>,
    @SerializedName("all")
    val all:String
){

}
