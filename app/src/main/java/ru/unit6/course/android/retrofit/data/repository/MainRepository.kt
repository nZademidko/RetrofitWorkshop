package ru.unit6.course.android.retrofit.data.repository

import ru.unit6.course.android.retrofit.data.api.ApiHelper
import ru.unit6.course.android.retrofit.data.model.User

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

    suspend fun getCurrentUser(userId:String) = apiHelper.getCurrentUser(userId = userId)

    suspend fun setUser(user: User) = apiHelper.setUser(user = user)
}