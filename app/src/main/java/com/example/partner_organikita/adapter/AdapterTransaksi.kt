package com.example.partner_organikita.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.front_startup.helper.Helper
import com.example.partner_organikita.R
import com.example.partner_organikita.model.TransactionModel
import kotlinx.android.synthetic.main.fragment_transaksi.*
import kotlin.collections.ArrayList

class AdapterTransaksi(var data: ArrayList<TransactionModel>, var listener: Listeners): RecyclerView.Adapter<AdapterTransaksi.Holder>() {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMethod = view.findViewById<TextView>(R.id.tvmethod)
        val tvNamaCustomer = view.findViewById<TextView>(R.id.tvnamacustomer)
        val tvTanggal = view.findViewById<TextView>(R.id.tvtanggal)
        val tvTotal = view.findViewById<TextView>(R.id.tvtotal)
        val tvQty = view.findViewById<TextView>(R.id.tvqty)
        val tvStatus = view.findViewById<TextView>(R.id.tvstatus)
        val btnCek = view.findViewById<Button>(R.id.btncek)
    }

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcv_transaksi, parent, false)
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val a = data[position]

        holder.tvMethod.text = a.transactionMethod
        holder.tvNamaCustomer.text = a.customer.customerName
        val formatBaru = "d MMM yyyy"
        holder.tvTanggal.text = Helper().convertTanggal(a.transactionCreatedAt, formatBaru)
        holder.tvTotal.text = Helper().changeCurrency(a.transactionTotalTransfer)
        holder.tvStatus.text = a.transactionStatus
        holder.tvQty.text = "Total item : " +  a.transactionTotalItem

        holder.btnCek.setOnClickListener{
            listener.onClicked(a)
        }
    }

    interface Listeners{
        fun onClicked(data: TransactionModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}