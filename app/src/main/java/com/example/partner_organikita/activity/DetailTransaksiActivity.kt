package com.example.partner_organikita.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.front_startup.helper.Helper
import com.example.partner_organikita.R
import com.example.partner_organikita.adapter.AdapterProductTransaction
import com.example.partner_organikita.model.DetailTransactionModel
import com.example.partner_organikita.model.TransactionModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import java.net.URLEncoder

class DetailTransaksiActivity : AppCompatActivity() {

    var transaksi = TransactionModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)

        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json, TransactionModel::class.java)

        setData(transaksi)
        displayProduct(transaksi.details)
        mainButton()
    }

    override fun onResume() {
        setData(transaksi)
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun mainButton(){
        btnback.setOnClickListener {
            onBackPressed()
        }

        btnhelp.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@DetailTransaksiActivity, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(this@DetailTransaksiActivity).inflate(
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

        btnatur.setOnClickListener {
            val json = Gson().toJson(transaksi, TransactionModel::class.java)
            val intent = Intent(this, ChooseDelivery::class.java)
            intent.putExtra("transaksi", json)
            startActivity(intent)
        }

        btntandai.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@DetailTransaksiActivity, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(this@DetailTransaksiActivity).inflate(
                R.layout.layout_sheet_sampai,
                findViewById(R.id.bottomSheet)
            )

            bottomSheetView.findViewById<Button>(R.id.btnclose)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<Button>(R.id.btnkembali)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<Button>(R.id.btnyakin)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        btntandaipickup.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@DetailTransaksiActivity, R.style.BottomSheetDialogTheme
            )

            val bottomSheetView = LayoutInflater.from(this@DetailTransaksiActivity).inflate(
                R.layout.layout_sheet_pickup,
                findViewById(R.id.bottomSheetPickup)
            )

            bottomSheetView.findViewById<Button>(R.id.btnclosepickup)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<Button>(R.id.btnkembalipickup)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<Button>(R.id.btnyakinpickup)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun setData(t: TransactionModel) {
        if (t.transactionDeliveryDetail == 1){
            llsudahatur.visibility = View.VISIBLE
            div_footer.visibility = View.GONE
            llsudahpick.visibility = View.GONE
        } else if (t.transactionDeliveryDetail == 0){
            llsudahatur.visibility = View.GONE
            div_footer.visibility = View.VISIBLE
            llsudahpick.visibility = View.GONE
        } else {
            llsudahatur.visibility = View.GONE
            div_footer.visibility = View.GONE
            llsudahpick.visibility = View.VISIBLE
        }

        val formatBaru = "d MMM yyyy"

        if (t.transactionDescription == ""){
            llcatatan.visibility = View.GONE
        }else{
            llcatatan.visibility = View.VISIBLE
        }
        tvcatatan.text = t.transactionDescription
        tvinvoice.text = t.transactionCode
        tv_status.text = t.transactionStatus
        tv_tanggal.text = Helper().convertTanggal(t.transactionCreatedAt, formatBaru)
        tv_nama.text = t.transactionName
        tv_notelp.text = t.transactionPhone
        tv_alamat.text = t.transactionLocationDetail
        tv_totalbelanja.text = Helper().changeCurrency(t.transactionTotalPrice)
        tv_ongkir.text = Helper().changeCurrency(t.transactionCostShipping)
        tv_total.text = Helper().changeCurrency(t.transactionTotalTransfer)

        if (t.transactionStatus == "BELUM BAYAR"){
            div_footer.visibility = View.GONE
        } else if (t.transactionStatus == "BATAL"){
            div_footer.visibility = View.GONE
        }
    }

    fun displayProduct(transaksis: ArrayList<DetailTransactionModel>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rcv_product.adapter = AdapterProductTransaction(this, transaksis)
        rcv_product.layoutManager = layoutManager
    }
}