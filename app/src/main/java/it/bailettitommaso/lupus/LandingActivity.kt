package it.bailettitommaso.lupus

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import it.bailettitommaso.lupus.models.Resource
import it.bailettitommaso.lupus.models.User
import it.bailettitommaso.lupus.viewmodels.SelfUserViewModel
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Lupus_NoActionBar)
        setContentView(R.layout.landing_activity)

        val selfUserViewModel: SelfUserViewModel by viewModels()
        selfUserViewModel.getSelfUser().observe(this) {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    Log.d("LupusTest", it.message!!)
                }
                is Resource.Loading -> {
                    Log.d("LupusTest", "Loading...")
                }
                else -> {
                    Log.d("LupusTest", it.data.toString())
                }
            }
        }
    }
}