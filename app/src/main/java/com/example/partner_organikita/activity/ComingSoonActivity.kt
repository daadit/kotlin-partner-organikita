package com.example.partner_organikita.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import com.example.partner_organikita.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_coming_soon.*
import kotlinx.android.synthetic.main.activity_hubungi_kami.*
import java.net.URLEncoder

class ComingSoonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coming_soon)

        btnbackcoming.setOnClickListener {
            onBackPressed()
        }

        btnhelpcoming.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@ComingSoonActivity, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(this@ComingSoonActivity).inflate(
                R.layout.layout_bottom_callcenter,
                findViewById(R.id.bottomSheetCallCenter)
            )

            bottomSheetView.findViewById<Button>(R.id.btnclosecallcenter)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<Button>(R.id.btnchat)?.setOnClickListener {
                val number = "6282171538531"
                val message = "Hai Admin, Saya butuh bantuan ketika menggunakan aplikasi Organikita Partner. Masalah saya adalah ... . Apa yang harus saya lakukan?"
                bottomSheetDialog.dismiss()
                val url = "https://api.whatsapp.com/send?phone="+ number +"&text=" + URLEncoder.encode(message, "UTF-8")
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}