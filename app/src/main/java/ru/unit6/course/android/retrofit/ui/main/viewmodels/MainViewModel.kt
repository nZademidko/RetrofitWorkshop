package ru.unit6.course.android.retrofit.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.unit6.course.android.retrofit.data.api.ApiHelper
import ru.unit6.course.android.retrofit.data.api.RetrofitBuilder
import ru.unit6.course.android.retrofit.data.repository.MainRepository
import ru.unit6.course.android.retrofit.utils.Resource

class MainViewModel : ViewModel() {

    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val mainRepository: MainRepository = MainRepository(apiHelper)

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}