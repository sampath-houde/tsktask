
package com.task.tsjtask.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.task.tsjtask.utils.showError
import com.task.tsjtask.utils.toastShort
import com.task.tsjtask.MainActivity
import com.task.tsjtask.R
import com.task.tsjtask.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val ERROR = "This field is required."
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.bind(view)

        auth = Firebase.auth


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        auth.currentUser.let {
            if(it == null) showLoginScreen()
            else {
                if(it.isEmailVerified) {
                    toastShort(requireContext(), "Logged In")
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                } else {
                    showLoginScreen()
                }
            }
        }
    }

    private fun showLoginScreen() {
        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.signIn.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            val bool = checkValidityOfLoginFields(email, password)

            if (bool) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            val currentUser = auth.currentUser
                            if(currentUser!!.isEmailVerified) {
                                toastShort(requireContext(), "Logged In")
                                startActivity(Intent(requireActivity(), MainActivity::class.java))
                                requireActivity().finish()
                            } else {
                                toastShort(requireContext(), "Verify Email")
                            }
                        } else {
                            toastShort(requireContext(), "Authentication Failed")
                        }
                    }
            }

        }
    }


    fun checkValidityOfLoginFields(email: String, password: String): Boolean {
        val isAllFieldsValid: Boolean

        if (email.isEmpty()) {
            isAllFieldsValid = false
            showError(binding.emailInputLayout, ERROR)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            val message = "Email Invalid"
            showError(binding.emailInputLayout, message)
            isAllFieldsValid = false
        } else if (password.isEmpty()) {
            showError(binding.passwordInputLayout, ERROR)
            isAllFieldsValid = false
        } else if (password.length < 5) {
            val message = "Password length should be greater than 5"
            showError(binding.passwordInputLayout, message)
            isAllFieldsValid = false
        } else isAllFieldsValid = true

        return isAllFieldsValid

    }
}