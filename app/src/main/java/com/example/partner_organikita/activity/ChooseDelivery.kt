package com.example.partner_organikita.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.partner_organikita.R
import com.example.partner_organikita.app.ApiConfig
import com.example.partner_organikita.model.ResponseModel
import com.example.partner_organikita.model.TransactionModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_choose_delivery.*
import retrofit2.Call
import retrofit2.Response
import java.net.URLEncoder

class ChooseDelivery : AppCompatActivity() {

    var transaksi = TransactionModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_delivery)

        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json, TransactionModel::class.java)

        setData(transaksi)
        mainButton()
    }

    private fun mainButton(){
        btnlanjut.setOnClickListener {
            if (rdsendiri.isChecked == true){
                ApiConfig.instanceRetrofit.changeStatus(transaksi.transactionId).enqueue(object :
                    retrofit2.Callback<ResponseModel> {

                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                        val res = response.body()!!
                        if(res.success == 1)
                        {

                        }
                    }
                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

                    }
                })
                ApiConfig.instanceRetrofit.changeDeliveryDetail(transaksi.transactionId, 1).enqueue(object :
                    retrofit2.Callback<ResponseModel> {

                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                        val res = response.body()!!
                        if(res.success == 1)
                        {

                        }
                    }
                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

                    }
                })
                val intent = Intent(this, SuccessActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            else
            {
                ApiConfig.instanceRetrofit.changeStatus(transaksi.transactionId).enqueue(object :
                    retrofit2.Callback<ResponseModel> {

                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                        val res = response.body()!!
                        if(res.success == 1)
                        {

                        }
                    }
                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

                    }
                })
                ApiConfig.instanceRetrofit.changeDeliveryDetail(transaksi.transactionId, 2).enqueue(object :
                    retrofit2.Callback<ResponseModel> {

                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                        val res = response.body()!!
                        if(res.success == 1)
                        {

                        }
                    }
                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

                    }
                })
                val intent = Intent(this, SuccessActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }

        llsendiri.setOnClickListener {
            llsendiri.setBackgroundResource(R.drawable.bg_green)
            llorganikita.setBackgroundResource(R.drawable.bg_white)
            rdsendiri.isChecked = true
            rdorganikita.isChecked = false
            tvjudulsendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
            tvisisendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
            tvketerangansendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
            tvjudulorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
            tvisiorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
            tvketeranganorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
        }

        rdsendiri.setOnClickListener {
            llsendiri.setBackgroundResource(R.drawable.bg_green)
            llorganikita.setBackgroundResource(R.drawable.bg_white)
            rdsendiri.isChecked = true
            rdorganikita.isChecked = false
            tvjudulsendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
            tvisisendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
            tvketerangansendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
            tvjudulorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
            tvisiorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
            tvketeranganorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
        }

        llorganikita.setOnClickListener {
            llorganikita.setBackgroundResource(R.drawable.bg_green)
            llsendiri.setBackgroundResource(R.drawable.bg_white)
            rdsendiri.isChecked = false
            rdorganikita.isChecked = true
            tvjudulsendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
            tvisisendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
            tvketerangansendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
            tvjudulorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
            tvisiorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
            tvketeranganorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
        }

        rdorganikita.setOnClickListener {
            llorganikita.setBackgroundResource(R.drawable.bg_green)
            llsendiri.setBackgroundResource(R.drawable.bg_white)
            rdsendiri.isChecked = false
            rdorganikita.isChecked = true
            tvjudulsendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
            tvisisendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
            tvketerangansendiri.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.customgray))
            tvjudulorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
            tvisiorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
            tvketeranganorganikita.setTextColor(ContextCompat.getColor(this@ChooseDelivery,R.color.white))
        }

        btnback.setOnClickListener {
            onBackPressed()
        }

        btnhelp.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@ChooseDelivery, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(this@ChooseDelivery).inflate(
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
    }

    @SuppressLint("SetTextI18n")
    fun setData(t: TransactionModel) {
        if (t.transactionMethod == "COD")
        {
            tvketerangansendiri.text = "Ongkos kirim langsung diberikan pembeli ke kamu saat pesanan sudah sampai"
            tvketeranganorganikita.text = "Pembayaran akan diteruskan setelah produk sampai ke pembeli"
        }
        else
        {
            tvketerangansendiri.text = "Ongkos kirim akan ditransfer ke rekening kamu saat pesanan sudah sampai"
            tvketeranganorganikita.text = "Pembayaran akan ditransfer setelah produk sampai ke pembeli"
        }
    }

}