package ru.unit6.course.android.retrofit.data.api

import ru.unit6.course.android.retrofit.data.model.User

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers() = apiService.getUsers()

    suspend fun getCurrentUser(userId: String) = apiService.getUser(userId = userId)

    suspend fun setUser(user: User) = apiService.setUser(user = user)

}