package ru.unit6.course.android.retrofit.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.unit6.course.android.retrofit.data.api.ApiHelper
import ru.unit6.course.android.retrofit.data.api.RetrofitBuilder
import ru.unit6.course.android.retrofit.data.model.User
import ru.unit6.course.android.retrofit.data.repository.MainRepository
import ru.unit6.course.android.retrofit.utils.Resource
import java.lang.Exception

class AddPersonViewModel : ViewModel() {

    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val mainRepository: MainRepository = MainRepository(apiHelper)


    fun setUser(name: String, email: String, avatar: String) =
        liveData(Dispatchers.IO) {
            if (name.isBlank() || email.isBlank() || avatar.isBlank()) {
                emit(Resource.error(data = null, "Empty something"))
            } else {
                emit(Resource.loading(data = null))
                try {
                    emit(
                        Resource.success(
                            data = mainRepository.setUser(
                                User(
                                    name = name,
                                    email = email,
                                    avatar = avatar
                                )
                            )
                        )
                    )
                } catch (e: Exception) {
                    emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))

                }
            }

        }
}