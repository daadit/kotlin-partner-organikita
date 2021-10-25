package com.example.partner_organikita.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.partner_organikita.R
import com.example.partner_organikita.activity.DetailTransaksiActivity
import com.example.partner_organikita.adapter.AdapterTransaksi
import com.example.partner_organikita.app.ApiConfig
import com.example.partner_organikita.helper.SharedPref
import com.example.partner_organikita.model.ResponseModel
import com.example.partner_organikita.model.TransactionModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_dikirim.*
import retrofit2.Call
import retrofit2.Response

class DikirimFragment : Fragment() {

    lateinit var layoutshimmer: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_dikirim, container, false)

        init(view)
        return view
    }

    fun init(view: View) {
        layoutshimmer = view.findViewById(R.id.shimmercoltwo)
    }

    fun getRiwayat(){
        val id = SharedPref(requireActivity()).getUser()!!.storeId
        ApiConfig.instanceRetrofit.getHistoryByStatus(id, "DIKIRIM").enqueue(object :
            retrofit2.Callback<ResponseModel> {

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val res = response.body()!!
                if(res.success == 1)
                {
                    if(activity == null){
                        return
                    }else {
                        layoutshimmer.stopShimmer()
                        layoutshimmer.visibility = View.GONE

                        if (res.istheretransaction == true){
                            rltidakadadikirim!!.visibility = View.GONE
                            rcvriwayatdikirim.visibility = View.VISIBLE
                            displayRiwayat(res.transaksis)
                        }
                        else
                        {
                            rltidakadadikirim.visibility = View.VISIBLE
                            rcvriwayatdikirim.visibility = View.GONE
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

            }
        })
    }

    fun displayRiwayat(transaksis: ArrayList<TransactionModel>) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rcvriwayatdikirim.adapter = AdapterTransaksi(transaksis, object : AdapterTransaksi.Listeners{
            override fun onClicked(data: TransactionModel) {
                val json = Gson().toJson(data, TransactionModel::class.java)
                val intent = Intent(requireActivity(), DetailTransaksiActivity::class.java)
                intent.putExtra("transaksi", json)
                startActivity(intent)
            }

        })
        rcvriwayatdikirim.layoutManager = layoutManager
    }

    override fun onResume() {
        getRiwayat()
        super.onResume()
    }
}