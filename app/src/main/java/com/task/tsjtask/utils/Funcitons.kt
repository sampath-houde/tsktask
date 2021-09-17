package com.task.tsjtask.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.task.tsjtask.R

fun Fragment.toastShort(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showError(editTextLayout: TextInputLayout, message: String) {
    editTextLayout.apply {
        error = message
        setErrorIconDrawable(R.drawable.ic_baseline_error_24)
    }
}

fun Activity.toastShort(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry"){
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(
    failure: ApiResponseHandler.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> view?.snackbar("Please check your internet connection", retry)

        failure.errorCode == 401 -> { //access token expired
            requireView().snackbar("Session Expired!! Login Again")
        }
        else -> {
            val error = failure.errorBody.toString()
            Log.d("ApiError", "Api Error $error")
        }
    }
}
