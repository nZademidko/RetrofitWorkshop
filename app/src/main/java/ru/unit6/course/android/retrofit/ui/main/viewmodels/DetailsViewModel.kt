package ru.unit6.course.android.retrofit.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.unit6.course.android.retrofit.data.api.ApiHelper
import ru.unit6.course.android.retrofit.data.api.RetrofitBuilder
import ru.unit6.course.android.retrofit.data.repository.MainRepository
import ru.unit6.course.android.retrofit.utils.Resource
import java.lang.Exception

class DetailsViewModel : ViewModel() {

    private val apiHelper = ApiHelper(RetrofitBuilder.apiService)
    private val mainRepository: MainRepository = MainRepository(apiHelper)


    fun getCurrentUser(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getCurrentUser(id)))
        }catch (e: Exception){
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))

        }
    }
}