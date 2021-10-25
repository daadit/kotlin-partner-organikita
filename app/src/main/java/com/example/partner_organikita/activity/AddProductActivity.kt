package com.example.partner_organikita.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.partner_organikita.R
import com.example.partner_organikita.app.ApiConfig
import com.example.partner_organikita.model.ProductCategoryModel
import com.example.partner_organikita.model.ResponseModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_add_product.*
import retrofit2.Call
import retrofit2.Response
import java.net.URLEncoder
import java.util.ArrayList

class AddProductActivity : AppCompatActivity() {

    var categoryProduct = ProductCategoryModel()
    var idcategory = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        getCategory()
        mainButton()
    }

    private fun mainButton(){
        btnback.setOnClickListener {
            onBackPressed()
        }

        btnhelpaddproduct.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@AddProductActivity, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(this@AddProductActivity).inflate(
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

        btn_selanjutnya.setOnClickListener {
            selanjutnya()
        }
    }

    private fun selanjutnya(){
        spin_kitadd.visibility = View.VISIBLE

        if(txtnama.text.isEmpty()){
            txtnama.error = "Nama harus disi"
            txtnama.requestFocus()
            spin_kitadd.visibility = View.GONE
            return
        } else if(idcategory == 0){
            val errortext = spinner_category.selectedView as TextView
            errortext.error = "Kategori harus disi"
            errortext.requestFocus()
            spin_kitadd.visibility = View.GONE
            return
        } else if(txtdeskripsi.text.isEmpty()){
            txtdeskripsi.error = "Deskripsi harus disi"
            txtdeskripsi.requestFocus()
            spin_kitadd.visibility = View.GONE
            return
        }else if(txtharga.text.isEmpty()){
            txtharga.error = "Harga harus disi"
            txtharga.requestFocus()
            spin_kitadd.visibility = View.GONE
            return
        }else if(txtstok.text.isEmpty()){
            txtstok.error = "Stok harus disi"
            txtstok.requestFocus()
            spin_kitadd.visibility = View.GONE
            return
        }else if(txtsatuan.text.isEmpty()){
            txtsatuan.error = "Satuan harus disi"
            txtsatuan.requestFocus()
            spin_kitadd.visibility = View.GONE
            return
        }else if(txtberat.text.isEmpty()){
            txtberat.error = "Berat harus disi"
            txtberat.requestFocus()
            spin_kitadd.visibility = View.GONE
            return
        }else if(txttinggi.text.isEmpty()){
            txttinggi.error = "Tinggi harus disi"
            txttinggi.requestFocus()
            spin_kitadd.visibility = View.GONE
            return
        }else if(txtlebar.text.isEmpty()){
            txtlebar.error = "Lebar harus disi"
            txtlebar.requestFocus()
            spin_kitadd.visibility = View.GONE
            return
        }
    }

    private var listCategory: ArrayList<ProductCategoryModel> = ArrayList()
    private fun getCategory(){
        ApiConfig.instanceRetrofit.getCategory().enqueue(object : retrofit2.Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val res = response.body()!!
                if (response.isSuccessful){
                    val arrayString = ArrayList<String>()
                    arrayString.add("Pilih kategori")

                    listCategory = res.productcategory
                    for (c in listCategory){
                        arrayString.add(c.productCategoryName)
                    }

                    val adapter = ArrayAdapter<Any>(this@AddProductActivity, R.layout.spinner, arrayString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner_category.adapter = adapter

                    spinner_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            if (position != 0){
                                categoryProduct = listCategory[position - 1]
                                idcategory = categoryProduct.productCategoryId
                            }
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
                } else {
                    Log.d("Error", "gagal memuat data:" + response.message())
                }
            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}