package com.redskill.icakes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.redskill.icakes.repository.CakesRepository

class CakesViewModelFactory(private val repository: CakesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CakesViewModel::class.java)) {
            CakesViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}