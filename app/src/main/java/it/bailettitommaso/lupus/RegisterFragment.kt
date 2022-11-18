package it.bailettitommaso.lupus

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.bailettitommaso.lupus.databinding.FragmentRegisterBinding
import it.bailettitommaso.lupus.models.Resource
import it.bailettitommaso.lupus.viewmodels.RegisterViewModel

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bindings: FragmentRegisterBinding = FragmentRegisterBinding.bind(view)

        bindings.fragmentRegistrationButtonRegister.setOnClickListener {

            val password = bindings.fragmentRegistrationEditTextPassword.text.toString()
            val passwordConfirmation =
                bindings.fragmentRegistrationEditTextPasswordConfirmation.text.toString()

            if (password != passwordConfirmation) {
                bindings.fragmentRegistrationEditTextPassword.text = null
                bindings.fragmentRegistrationEditTextPassword.requestFocus()
                bindings.fragmentRegistrationEditTextPassword.error = "Passwords do not match."
                bindings.fragmentRegistrationEditTextPasswordConfirmation.text = null
                bindings.fragmentRegistrationEditTextPasswordConfirmation.error =
                    "Passwords do not match."
            } else {
                registerViewModel.register(
                    bindings.fragmentRegistrationEditTextName.text.toString(),
                    bindings.fragmentRegistrationEditTextEmail.text.toString(),
                    bindings.fragmentRegistrationEditTextPassword.text.toString()
                ).observe(viewLifecycleOwner) { resource ->
                    if (resource is Resource.Loading) {
                        bindings.fragmentRegistrationButtonRegister.isClickable = false
                        bindings.fragmentRegistrationButtonRegister.text =
                            getString(R.string.loading)
                    } else {
                        if (resource is Resource.Error) {
                            Toast.makeText(
                                requireContext(), resource.error!!.message, Toast.LENGTH_SHORT
                            ).show()
                            resource.error.validationErrors?.onEachIndexed { index, field ->
                                val editText: EditText? = when (field.key) {
                                    "name" -> bindings.fragmentRegistrationEditTextName
                                    "email" -> bindings.fragmentRegistrationEditTextEmail
                                    "password" -> bindings.fragmentRegistrationEditTextPassword
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
                        } else {
                            findNavController().navigate(R.id.landingFragment)
                        }
                        bindings.fragmentRegistrationButtonRegister.isClickable = true
                        bindings.fragmentRegistrationButtonRegister.text =
                            getString(R.string.register)
                    }
                }
            }
        }
    }
}