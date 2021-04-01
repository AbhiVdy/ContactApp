package com.core.contactapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.core.contactapp.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch)
        GlobalScope.launch {
            delay(4000)
            navigateToActivity()
        }
    }

    private fun navigateToActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}