package com.example.partner_organikita.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.partner_organikita.MainActivity
import com.example.partner_organikita.R
import com.example.partner_organikita.app.ApiConfig
import com.example.partner_organikita.helper.SharedPref
import com.example.partner_organikita.model.ResponseModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

class LoginActivity : AppCompatActivity() {

    lateinit var s: SharedPref
    var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        s = SharedPref(this)

        mainButton()
    }

    private fun mainButton(){
        btn_login.setOnClickListener {
            login()
        }

        btnhelp.setOnClickListener {
            callCenterLayout()
        }

        callcenter.setOnClickListener {
            callCenterLayout()
        }

        txtLupaSandi.setOnClickListener {
            callCenterLayout()
        }
    }

    fun callCenterLayout(){
        val bottomSheetDialog = BottomSheetDialog(
            this@LoginActivity, R.style.BottomSheetDialogTheme
        )
        val bottomSheetView = LayoutInflater.from(this@LoginActivity).inflate(
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

    fun login(){
        if(txtemail.text.isEmpty()){
            txtemail.error = "Email harus disi"
            txtemail.requestFocus()
            return
        } else if(txtpassword.text.isEmpty()){
            txtpassword.error = "Email harus disi"
            txtpassword.requestFocus()
            return
        }

        loadinglogin.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.login(txtemail.text.toString(), txtpassword.text.toString()).enqueue(object :
            Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                loadinglogin.visibility = View.GONE
                val respon = response.body()!!
                if(respon.success == 1)
                {
                    Toast.makeText(this@LoginActivity, "Selamat datang", Toast.LENGTH_SHORT).show()
                    s.setStatusLogin(true)
                    s.setUser(respon.store)
                    val ip = Intent( this@LoginActivity, MainActivity::class.java)
                    ip.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(ip)
                    finish()
                }
                else
                {
                    Toast.makeText(this@LoginActivity, "Error :"+respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                loadinglogin.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Error :"+t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}