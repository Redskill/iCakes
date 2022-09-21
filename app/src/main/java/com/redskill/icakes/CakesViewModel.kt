package com.redskill.icakes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redskill.icakes.model.Cake
import com.redskill.icakes.repository.CakesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CakesViewModel constructor(private val cakesRepository: CakesRepository) : ViewModel() {

    val cakesList = MutableLiveData<List<Cake>>()
    var listIsLoaded = true // TODO not really happy about this, with more time I would follow this https://proandroiddev.com/retrofit-calladapter-for-either-type-2145781e1c20

    fun getAllCakes() {
        viewModelScope.launch {
            try {
                val response = cakesRepository.getAllCakes()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        cakesList.postValue(response.body() as List<Cake>)
                        listIsLoaded = true //TODO Ideally errors should be handled by an interceptor in retrofit instance and not via a boolean flag
                    } else {
                        viewModelScope.cancel()
                        Log.e("CAKES", "Failed to load cakes list")
                    }
                }
            } catch (e: Exception) {
                viewModelScope.cancel()
            }
        }
    }
}