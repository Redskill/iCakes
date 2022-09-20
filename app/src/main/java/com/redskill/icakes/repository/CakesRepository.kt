package com.redskill.icakes.repository

import com.redskill.icakes.utils.RetrofitService

class CakesRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllCakes() = retrofitService.getAllCakes()
}