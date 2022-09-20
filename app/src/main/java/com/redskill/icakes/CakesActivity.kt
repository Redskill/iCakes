package com.redskill.icakes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.redskill.icakes.databinding.ActivityCakesBinding
import com.redskill.icakes.repository.CakesRepository
import com.redskill.icakes.utils.RetrofitService

class CakesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCakesBinding
    private val retrofitService = RetrofitService.getInstance()
    private val cakesAdapter: CakesAdapter by lazy { CakesAdapter() }
    private val cakesRepository = CakesRepository(retrofitService)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCakesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(
            this,
            CakesViewModelFactory(cakesRepository)
        )[CakesViewModel::class.java]
        viewModel.getAllCakes()
        binding.cakesList.layoutManager = LinearLayoutManager(this)
        binding.cakesList.adapter = cakesAdapter
        viewModel.cakesList.observe(this) {
            cakesAdapter.submitList(it.distinct().sortedBy { it.title })
        }
    }
}