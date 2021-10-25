package com.example.partner_organikita.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import com.example.partner_organikita.MainActivity
import com.example.partner_organikita.R
import com.example.partner_organikita.helper.SharedPref

class SplashScreenActivity : AppCompatActivity() {

    private val Splash:Long = 3000 //delay 3 detik
    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        s = SharedPref(this)

        window.setStatusBarColor(ContextCompat.getColor(this@SplashScreenActivity,R.color.white))

        //Kode untuk menjalankan main screen setelah timer splash habis
        Handler().postDelayed({
            if(s.getStatusLogin()){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, Splash)
        //End Code
    }
}