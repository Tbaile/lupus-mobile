package it.bailettitommaso.lupus

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.bailettitommaso.lupus.databinding.FragmentLoginBinding
import it.bailettitommaso.lupus.models.Resource
import it.bailettitommaso.lupus.viewmodels.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding: FragmentLoginBinding = FragmentLoginBinding.bind(view)
        binding.fragmentLoginButtonLogin.setOnClickListener {
            loginViewModel.login(
                binding.fragmentLoginEditTextEmail.text.toString(),
                binding.fragmentLoginEditTextPassword.text.toString()
            ).observe(viewLifecycleOwner) { resource ->
                if (resource is Resource.Loading) {
                    binding.fragmentLoginButtonLogin.isClickable = false
                    binding.fragmentLoginButtonLogin.text = getString(R.string.loading)
                } else {
                    binding.fragmentLoginButtonLogin.isClickable = true
                    binding.fragmentLoginButtonLogin.text = getString(R.string.login)
                }
                if (resource is Resource.Error) {
                    Toast.makeText(requireContext(), resource.error!!.message, Toast.LENGTH_SHORT)
                        .show()
                    resource.error.validationErrors?.onEachIndexed { index, field ->
                        val editText: EditText? = when (field.key) {
                            "email" -> binding.fragmentLoginEditTextEmail
                            "password" -> binding.fragmentLoginEditTextPassword
                            else -> null
                        }
                        editText?.let {
                            it.selectAll()
                            it.error = field.value.first()
                            if (index == 0) {
                                it.requestFocus()
                            }
                        }
                    }
                } else if (resource is Resource.Success) {
                    findNavController().navigate(R.id.landingFragment)
                }
            }
        }

        binding.fragmentLoginButtonRegister.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }
}
