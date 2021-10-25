package com.example.partner_organikita.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.front_startup.helper.Helper
import com.example.partner_organikita.R
import com.example.partner_organikita.model.ProductModel
import com.example.partner_organikita.util.Config
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterProduct(var activity: Activity, var data: ArrayList<ProductModel>, var listener: Listeners): RecyclerView.Adapter<AdapterProduct.Holder>() {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvStatus = view.findViewById<TextView>(R.id.tvstatusproduk)
        val tvNamaProduk = view.findViewById<TextView>(R.id.tvnamaproduk)
        val tvTanggal = view.findViewById<TextView>(R.id.tvtanggal)
        val tvHarga = view.findViewById<TextView>(R.id.tvharga)
        val tvQty = view.findViewById<TextView>(R.id.tvqty)
        val ivGambar = view.findViewById<ImageView>(R.id.ivgambarproduk)
    }

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcv_product, parent, false)
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val a = data[position]

        holder.tvNamaProduk.text = a.productName
        val formatBaru = "d MMM yyyy"
        holder.tvHarga.text = Helper().changeCurrency(a.productPrice)
        holder.tvQty.text = "Stok : " +  a.productStock
        if (a.productStatus == 1){
            holder.tvStatus.text = "PUBLISH"
        }else{
            holder.tvStatus.text = "PENDING"
        }

        val img = Config.productUrl + a.productImage
        Glide
            .with(activity)
            .load(img)
            .placeholder(R.drawable.organikita)
            .error(R.drawable.organikita)
            .centerCrop()
            .into(holder.ivGambar)
    }

    interface Listeners{
        fun onClicked(data: ProductModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}