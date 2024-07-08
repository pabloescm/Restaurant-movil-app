package com.moviles.viewmodels



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviles.restaurantes.repositories.RestaurantRepository
import okhttp3.MultipartBody

class UploadLogoViewModel : ViewModel() {

    private val repository = RestaurantRepository

    private val _selectedFilePath = MutableLiveData<String>()
    val selectedFilePath: LiveData<String>
        get() = _selectedFilePath

    fun setSelectedFilePath(filePath: String) {
        _selectedFilePath.value = filePath
    }

    fun uploadLogo(
        token: String,
        restaurantId: Int,
        file: MultipartBody.Part,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        repository.uploadLogo(token, restaurantId, file,
            success = { success() },
            failure = { throwable -> failure(throwable) }
        )
    }
}
