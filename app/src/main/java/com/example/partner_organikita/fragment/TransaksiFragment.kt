package com.example.partner_organikita.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.partner_organikita.R
import com.example.partner_organikita.activity.DetailTransaksiActivity
import com.example.partner_organikita.adapter.AdapterTransaksi
import com.example.partner_organikita.app.ApiConfig
import com.example.partner_organikita.helper.SharedPref
import com.example.partner_organikita.model.ResponseModel
import com.example.partner_organikita.model.TransactionModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_transaksi.*
import retrofit2.Call
import retrofit2.Response

class TransaksiFragment : Fragment() {

    lateinit var swipeToResfreh: SwipeRefreshLayout
    lateinit var layoutshimmer: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_transaksi, container, false)
        init(view)

        refreshApp()
        return view
    }

    fun init(view: View) {
        swipeToResfreh = view.findViewById(R.id.swipeToRefreshTransaksi)
        layoutshimmer = view.findViewById(R.id.shimmercoltransaksi)
    }

    private fun refreshApp(){
        swipeToResfreh.setOnRefreshListener {
            getRiwayat()
            swipeToResfreh.isRefreshing = false
        }
    }

    fun getRiwayat(){
        if(activity == null){
            return
        }else {
            val id = SharedPref(requireActivity()).getUser()!!.storeId
            ApiConfig.instanceRetrofit.getHistory(id).enqueue(object :
                retrofit2.Callback<ResponseModel> {

                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    val res = response.body()!!
                    if (res.success == 1) {
                        if(activity == null){
                            return
                        }else {
                            layoutshimmer.stopShimmer()
                            layoutshimmer.visibility = View.GONE

                            rcvtransaksi.visibility = View.VISIBLE
                            displayRiwayat(res.transaksis)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

                }
            })
        }
    }

    fun displayRiwayat(transaksis: ArrayList<TransactionModel>) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rcvtransaksi.adapter = AdapterTransaksi(transaksis, object : AdapterTransaksi.Listeners{
            override fun onClicked(data: TransactionModel) {
                val json = Gson().toJson(data, TransactionModel::class.java)
                val intent = Intent(requireActivity(), DetailTransaksiActivity::class.java)
                intent.putExtra("transaksi", json)
                startActivity(intent)
            }

        })
        rcvtransaksi.layoutManager = layoutManager
    }

    override fun onResume() {
        getRiwayat()
        super.onResume()
    }
}