package com.example.partner_organikita.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.partner_organikita.R
import kotlinx.android.synthetic.main.activity_kebijakan_privasi.*

class KebijakanPrivasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kebijakan_privasi)

        btnback.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}