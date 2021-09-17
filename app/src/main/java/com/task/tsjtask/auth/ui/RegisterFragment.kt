package com.task.tsjtask.auth.ui

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.task.solutiondeveloper.utils.*
import com.task.tsjtask.R
import com.task.tsjtask.auth.model.User
import com.task.tsjtask.databinding.FragmentRegisterBinding
import com.task.tsjtask.utils.Constants
import com.task.tsjtask.utils.LoadingDialog
import com.task.tsjtask.utils.showError
import com.task.tsjtask.utils.toastShort
import java.util.*

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private val ERROR = "This field is required."
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        binding = FragmentRegisterBinding.bind(view)

        loadingDialog = LoadingDialog(requireActivity())

        auth = Firebase.auth

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        showUI()

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }


    }

    private fun showUI() {

        binding.createAccountButton.setOnClickListener {

            val name = binding.firstNameEditText.text.toString().trim()
            val email = binding.emailEditText2.text.toString().trim()
            val password = binding.passwordEditText2.text.toString().trim()



            val bool = checkValidityOfRegisterFields(name, email, password)

            if (bool) {
                loadingDialog.startLoading()
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        loadingDialog.stopLoading()
                        if(it.isSuccessful) {
                            val current = auth.currentUser!!
                            toastShort(requireContext(), "Verification email sent to $email" )
                            current.sendEmailVerification()
                        } else {
                            toastShort(requireContext(), "Error")
                        }
                    }
            }

        }
    }


    fun checkValidityOfRegisterFields(
        name: String,
        email: String,
        password: String,
    ): Boolean {

        var isAllFieldsValid = false

        if (email.isEmpty()) {
            showError(binding.emailInputLayout2, ERROR)
            isAllFieldsValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(binding.emailInputLayout2, "Email Invalid")
            isAllFieldsValid = false
        } else if (password.isEmpty()) {
            showError(binding.passwordInputLayout2, ERROR)
            isAllFieldsValid = false
        } else if (password.length < 5) {
            showError(binding.passwordInputLayout2, "Password length should be greater than 5")
            isAllFieldsValid = false
        } else if (name.isEmpty()) {
            showError(binding.firstNameInputLayout, ERROR)
            isAllFieldsValid = false
        } else isAllFieldsValid = true

        return isAllFieldsValid
    }


}