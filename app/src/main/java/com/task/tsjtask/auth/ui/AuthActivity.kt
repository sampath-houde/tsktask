package com.task.tsjtask.auth.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.task.tsjtask.R

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        window?.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryVariant)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window?.statusBarColor = ContextCompat.getColor(applicationContext, R.color.black)
    }
}