package it.bailettitommaso.lupus

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.bailettitommaso.lupus.models.Resource
import it.bailettitommaso.lupus.viewmodels.SelfUserViewModel

@AndroidEntryPoint
class LandingFragment : Fragment(R.layout.fragment_landing) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selfUserViewModel: SelfUserViewModel by viewModels()
        selfUserViewModel.getSelfUser().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    val error: String = if (it.status == 401) {
                        "Login expired, please login again."
                    } else {
                        it.error!!.message
                    }
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_login_required)
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