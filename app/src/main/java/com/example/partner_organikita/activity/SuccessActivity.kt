package com.example.partner_organikita.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import com.example.partner_organikita.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_success.*
import java.net.URLEncoder

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        mainButton()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun mainButton(){
        btnlihat.setOnClickListener {
            onBackPressed()
        }

        btnhelp.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@SuccessActivity, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(this@SuccessActivity).inflate(
                R.layout.layout_bottom_callcenter,
                findViewById(R.id.bottomSheetCallCenter)
            )

            bottomSheetView.findViewById<Button>(R.id.btnclosecallcenter)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<Button>(R.id.btnchat)?.setOnClickListener {
                val number = "6282171538531"
                val message =
                    "Hai Admin, Saya butuh bantuan ketika menggunakan aplikasi Organikita Partner. Masalah saya adalah ... . Apa yang harus saya lakukan?"
                bottomSheetDialog.dismiss()
                val url =
                    "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(
                        message,
                        "UTF-8"
                    )
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        btnback.setOnClickListener {
            onBackPressed()
        }
    }
}