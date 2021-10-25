package com.example.partner_organikita.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.partner_organikita.R
import kotlinx.android.synthetic.main.activity_hubungi_kami.*

class HubungiKamiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hubungi_kami)

        btnback.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}