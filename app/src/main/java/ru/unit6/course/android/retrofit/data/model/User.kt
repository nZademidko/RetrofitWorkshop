package ru.unit6.course.android.retrofit.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar")
    val avatar: String = "-1",
    @SerializedName("email")
    val email: String = "-1",
    @SerializedName("id")
    val id: String = "-1",
    @SerializedName("name")
    val name: String = "-1",
)
