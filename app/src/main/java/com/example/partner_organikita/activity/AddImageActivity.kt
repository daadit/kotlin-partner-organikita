package com.example.partner_organikita.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.front_startup.helper.Helper
import com.example.partner_organikita.R
import com.example.partner_organikita.app.ApiConfig
import com.example.partner_organikita.model.ProductModel
import com.example.partner_organikita.model.ResponseModel
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.inyongtisto.myhelper.extension.toMultipartBody
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_image.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        mainButton()
    }

    private fun mainButton(){
        btn_upload.setOnClickListener {
            dialogPilihUpload()
        }

        btn_kembali.setOnClickListener {
            onBackPressed()
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!
            val fileUri: Uri = uri
            dialogUpload(File(fileUri.path!!))
        }
    }

    private fun dialogPilihUpload(){
        val bottomSheetDialog = BottomSheetDialog(
            this@AddImageActivity, R.style.BottomSheetDialogTheme
        )
        val bottomSheetView = LayoutInflater.from(this@AddImageActivity).inflate(
            R.layout.layout_sheet_uploadbukti,
            findViewById(R.id.bottomSheetUpload)
        )

        bottomSheetView.findViewById<Button>(R.id.btncloseupload)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<Button>(R.id.btngaleri)?.setOnClickListener {
            bottomSheetDialog.dismiss()
            launcher.launch(
                ImagePicker.with(this)
                    .crop()
                    .maxResultSize(512, 512)
                    .galleryOnly()
                    .createIntent()
            )
        }

        bottomSheetView.findViewById<Button>(R.id.btnkamera)?.setOnClickListener {
            bottomSheetDialog.dismiss()
            launcher.launch(
                ImagePicker.with(this)
                    .crop()
                    .maxResultSize(512, 512)
                    .cameraOnly()
                    .createIntent()
            )
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    var alertDialog : AlertDialog? = null

    @SuppressLint("InflateParams")
    private fun dialogUpload(file: File){
        val view = layoutInflater
        val layout = view.inflate(R.layout.layout_view_bukti, null)

        val imagebukti: ImageView = layout.findViewById(R.id.imagebukti)
        val btnuploadfix: Button = layout.findViewById(R.id.btnuploadfix)
        val btnpilihyanglain: Button = layout.findViewById(R.id.btnpilihyglain)

        Picasso.get()
            .load(file)
            .into(imagebukti)

        btnpilihyanglain.setOnClickListener {
            dialogPilihUpload()
        }

        btnuploadfix.setOnClickListener {
            uploadFix(file)
        }

        alertDialog = AlertDialog.Builder(this).create()
        alertDialog!!.setView(layout)
        alertDialog!!.setCancelable(true)
        alertDialog!!.show()
    }

    private fun uploadFix(file: File){
        val json = intent.getStringExtra("extra")!!.toString()
        val produk = Gson().fromJson(json, ProductModel::class.java)

        ApiConfig.instanceRetrofit.saveProduct(produk).enqueue(object :
            Callback<ResponseModel> {

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val res = response.body()!!
                if(res.success == 1)
                {
                    uploadImage(res.produk.id, file)
                } else {
                }
            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("error", t.message!!)
            }
        })
    }

    private fun uploadImage(id: Int, file: File){
        val fileImage = file.toMultipartBody()
        ApiConfig.instanceRetrofit.saveProductImage(id, fileImage!!).enqueue(object :
            Callback<ResponseModel> {

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val res = response.body()!!
                if(res.success == 1)
                {
                } else {
                }
            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("error", t.message!!)
            }
        })
    }

}