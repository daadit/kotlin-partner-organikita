package com.example.partner_organikita.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.partner_organikita.R
import com.example.partner_organikita.activity.AddProductActivity
import com.example.partner_organikita.adapter.AdapterProduct
import com.example.partner_organikita.app.ApiConfig
import com.example.partner_organikita.helper.SharedPref
import com.example.partner_organikita.model.ProductModel
import com.example.partner_organikita.model.ResponseModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_product.*
import retrofit2.Call
import retrofit2.Response
import java.net.URLEncoder

class ProductFragment : Fragment() {

    lateinit var add: FloatingActionButton
    lateinit var swipeToResfreh: SwipeRefreshLayout
    lateinit var layoutshimmer: ShimmerFrameLayout
    lateinit var btnHelp: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_product, container, false)

        init(view)
        mainButton()
        refreshApp()
        return view
    }

    private fun refreshApp(){
        swipeToResfreh.setOnRefreshListener {
            getRiwayat()
            swipeToResfreh.isRefreshing = false
        }
    }

    private fun mainButton(){
        add.setOnClickListener {
            val intent = Intent(requireActivity(), AddProductActivity::class.java)
            startActivity(intent)
        }
        btnHelp.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_bottom_callcenter,
                view?.findViewById(R.id.bottomSheetCallCenter)
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

    fun getRiwayat(){
        val id = SharedPref(requireActivity()).getUser()!!.storeId
        ApiConfig.instanceRetrofit.getProduct(id).enqueue(object :
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

                        rcvproduksaya.visibility = View.VISIBLE
                        displayRiwayat(res.product)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

            }
        })
    }

    fun displayRiwayat(product: ArrayList<ProductModel>) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rcvproduksaya.adapter = AdapterProduct(requireActivity(), product, object : AdapterProduct.Listeners{
            override fun onClicked(data: ProductModel) {
            }

        })
        rcvproduksaya.layoutManager = layoutManager
    }

    private fun init(view: View){
        add = view.findViewById(R.id.fab_add)
        swipeToResfreh = view.findViewById(R.id.swipeToRefreshProduct)
        layoutshimmer = view.findViewById(R.id.shimmercolproduk)
        btnHelp = view.findViewById(R.id.btnhelp)
    }

    override fun onResume() {
        getRiwayat()
        super.onResume()
    }
}