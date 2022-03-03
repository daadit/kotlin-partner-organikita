package com.example.partner_organikita.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.front_startup.helper.Helper
import com.example.partner_organikita.R
import com.example.partner_organikita.app.ApiConfig
import com.example.partner_organikita.helper.SharedPref
import com.example.partner_organikita.model.ProductCategoryModel
import com.example.partner_organikita.model.ProductModel
import com.example.partner_organikita.model.ResponseModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_product.*
import retrofit2.Call
import retrofit2.Response
import java.net.URLEncoder
import java.util.ArrayList

class DetailProductActivity : AppCompatActivity() {

    var categoryProduct = ProductCategoryModel()
    var idcategory = 0

    lateinit var product: ProductModel
    var namaProduk = ""
    var productId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        getCategory()
        mainButton()
    }

    fun getInfo(){
        val data = intent.getStringExtra("extra")
        product = Gson().fromJson<ProductModel>(data, ProductModel::class.java)

        //set Value
        productId = product.productId
        txtnama.text = Editable.Factory.getInstance().newEditable(product.productName)
        spinner_category.setSelection(product.productCategory)
        namaProduk = product.productName
        txtdeskripsi.text = Editable.Factory.getInstance().newEditable(product.productDescription)
        txtnama.text = Editable.Factory.getInstance().newEditable(product.productName)
        txtharga.text = Editable.Factory.getInstance().newEditable(product.productPrice.toString())
        txtstok.text = Editable.Factory.getInstance().newEditable(product.productStock.toString())
        txtsatuan.text = Editable.Factory.getInstance().newEditable(product.productSatuan)
        txtberat.text = Editable.Factory.getInstance().newEditable(product.productWeight.toString())
        txttinggi.text = Editable.Factory.getInstance().newEditable(product.productLength.toString())
        txtlebar.text = Editable.Factory.getInstance().newEditable(product.productWide.toString())
        txtpanjang.text = Editable.Factory.getInstance().newEditable(product.productHigh.toString())

//        val img = Config.productUrl + product.productImage
//
//        Glide
//            .with(this)
//            .load(img)
//            .placeholder(R.drawable.logofix)
//            .error(R.drawable.logofix)
//            .centerCrop()
//            .into(productImage)
    }

    private fun mainButton(){
        btnback.setOnClickListener {
            onBackPressed()
        }

        btnhelpaddproduct.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@DetailProductActivity, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(this@DetailProductActivity).inflate(
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
        }else if(txtpanjang.text.isEmpty()){
            txtpanjang.error = "Panjang harus disi"
            txtpanjang.requestFocus()
            spin_kitadd.visibility = View.GONE
            return
        }

        val user = SharedPref(this).getUser()!!

        val produk = ProductModel()
        produk.productName = txtnama.text.toString()
        produk.productCategory = idcategory
        produk.productStore = user.storeId
        produk.productDescription = txtdeskripsi.text.toString()
        produk.productPrice = Integer.valueOf(txtharga.text.toString())
        produk.productStock = Integer.valueOf(txtstok.text.toString())
        produk.productRating = "5"
        produk.productSold = "1"
        produk.productStatus = 1
        produk.productSatuan = txtsatuan.text.toString()
        produk.productWeight = Integer.valueOf(txtberat.text.toString())
        produk.productHigh = Integer.valueOf(txttinggi.text.toString())
        produk.productWide = Integer.valueOf(txtlebar.text.toString())
        produk.productLength = Integer.valueOf(txtpanjang.text.toString())
        produk.productImage = "default_product.png"

        val json = Gson().toJson(produk, ProductModel::class.java)
        val intent = Intent(this, AddImageActivity::class.java)
        intent.putExtra("extra", json)
        startActivity(intent)

        spin_kitadd.visibility = View.GONE
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

                    val adapter = ArrayAdapter<Any>(this@DetailProductActivity, R.layout.spinner, arrayString.toTypedArray())
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
                    getInfo()
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