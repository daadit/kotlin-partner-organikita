package com.example.partner_organikita.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.partner_organikita.R
import kotlinx.android.synthetic.main.activity_ketentuan_layanan.*

class KetentuanLayananActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ketentuan_layanan)

        btnback.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}