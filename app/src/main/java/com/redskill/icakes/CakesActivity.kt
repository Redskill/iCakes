package com.redskill.icakes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.redskill.icakes.databinding.ActivityCakesBinding
import com.redskill.icakes.repository.CakesRepository
import com.redskill.icakes.utils.RetrofitService
import java.lang.Exception

class CakesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCakesBinding
    private var viewModel: CakesViewModel? = null
    private val retrofitService = RetrofitService.getInstance()
    private val cakesAdapter: CakesAdapter by lazy { CakesAdapter() }
    private val cakesRepository = CakesRepository(retrofitService)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCakesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            CakesViewModelFactory(cakesRepository)
        )[CakesViewModel::class.java]

        viewModel?.getAllCakes()
        // Display a generic message if the list is not loaded
        if (viewModel?.listIsLoaded == false) {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
        
        binding.cakesList.apply {
            layoutManager = LinearLayoutManager(this@CakesActivity)
            adapter = cakesAdapter
        }

        viewModel?.cakesList?.observe(this) {
            if (it.isEmpty()) {
                createNetworkAlertDialog()
            } else {
                cakesAdapter.submitList(it.distinct().sortedBy { it.title })
            }
        }
    }

    private fun createNetworkAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle(getString(R.string.error))
            setMessage(getString(R.string.Retry))
            setNegativeButton(context.getString(R.string.Cancel)) { dialogInterface, _ -> dialogInterface.dismiss() }
            setPositiveButton(context.getString(R.string.OK)) { dialogInterface, _ ->
                run {
                    viewModel?.getAllCakes()
                    dialogInterface?.dismiss()
                }
            }
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item) {
            Toast.makeText(this, R.string.Refreshing___, Toast.LENGTH_SHORT).show()
            try {
                viewModel?.getAllCakes()
            } catch (e: Exception) {
                Toast.makeText(this, R.string.Download_failed, Toast.LENGTH_SHORT).show()
                createNetworkAlertDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
