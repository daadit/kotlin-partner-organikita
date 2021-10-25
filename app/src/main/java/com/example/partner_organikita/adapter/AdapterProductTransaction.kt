package com.example.partner_organikita.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.front_startup.helper.Helper
import com.example.partner_organikita.R
import com.example.partner_organikita.model.DetailTransactionModel
import com.example.partner_organikita.util.Config
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterProductTransaction(var activity: Activity, var data: ArrayList<DetailTransactionModel>): RecyclerView.Adapter<AdapterProductTransaction.Holder>() {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tvnama)
        val tvHarga = view.findViewById<TextView>(R.id.tvhargat)
        val tvQty = view.findViewById<TextView>(R.id.tvqtyt)
        val tvTotal = view.findViewById<TextView>(R.id.tvtotalt)
        val ivGambar = view.findViewById<ImageView>(R.id.ivgambar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcv_produk_transaksi, parent, false)
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val a = data[position]

        holder.tvNama.text = a.product.productName
        holder.tvHarga.text = Helper().changeCurrency(a.product.productPrice)
        holder.tvQty.text = a.detailTotalItem.toString()
        holder.tvTotal.text = Helper().changeCurrency(a.detailTotalPrice)

        val img = Config.productUrl + a.product.productImage
        Glide
            .with(activity)
            .load(img)
            .placeholder(R.drawable.organikita)
            .error(R.drawable.organikita)
            .centerCrop()
            .into(holder.ivGambar)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}