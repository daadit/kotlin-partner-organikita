package com.example.partner_organikita.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.partner_organikita.R
import com.example.partner_organikita.activity.*
import com.example.partner_organikita.helper.SharedPref
import com.example.partner_organikita.util.Config
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso

class UserFragment : Fragment() {

    lateinit var swipeToResfrehUser: SwipeRefreshLayout
    lateinit var btnLogout: Button
    lateinit var s:SharedPref
    lateinit var txtName: TextView
    lateinit var txtEmail: TextView
    lateinit var txtNoHp: TextView
    lateinit var llketentuanlayanan: LinearLayout
    lateinit var llkebijakanprivasi: LinearLayout
    lateinit var llhubungikami: LinearLayout
    lateinit var llnilaikami: LinearLayout
    lateinit var list1: CardView
    lateinit var list2: CardView
    lateinit var list3: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_user, container, false)

        init(view)

        s = SharedPref(requireActivity())

        refreshApp()
        mainButton()
        setData()
        return view
    }

    private fun setData() {
        if(s.getUser() == null){
            val ip = Intent( activity, LoginActivity::class.java)
            ip.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(ip)
            return
        }
        val user = s.getUser()!!

        txtName.text = user.storeName
        txtEmail.text = user.storeEmail
        txtNoHp.text = user.storePhoneNumber
    }

    private fun mainButton(){
        btnLogout.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_sheet_logout,
                view?.findViewById(R.id.bottomSheetLogout)
            )

            bottomSheetView.findViewById<Button>(R.id.btncloselogout)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<Button>(R.id.btnnggakjadi)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<Button>(R.id.btnkeluar)?.setOnClickListener {
                bottomSheetDialog.dismiss()
                s.setStatusLogin(false)
                val ip = Intent( activity, LoginActivity::class.java)
                ip.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(ip)
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        llketentuanlayanan.setOnClickListener {
            val ip = Intent( activity, KetentuanLayananActivity::class.java)
            startActivity(ip)
        }

        llkebijakanprivasi.setOnClickListener {
            val ip = Intent( activity, KebijakanPrivasiActivity::class.java)
            startActivity(ip)
        }

        llhubungikami.setOnClickListener {
            val ip = Intent( activity, HubungiKamiActivity::class.java)
            startActivity(ip)
        }

        list1.setOnClickListener {
            val ip = Intent( activity, ComingSoonActivity::class.java)
            startActivity(ip)
        }

        list2.setOnClickListener {
            val ip = Intent( activity, ComingSoonActivity::class.java)
            startActivity(ip)
        }

        list3.setOnClickListener {
            val ip = Intent( activity, ComingSoonActivity::class.java)
            startActivity(ip)
        }
    }

    private fun refreshApp(){
        swipeToResfrehUser.setOnRefreshListener {
            swipeToResfrehUser.isRefreshing = false
        }
    }

    private fun init(view: View) {
        swipeToResfrehUser = view.findViewById(R.id.swipeToRefreshUser)
        btnLogout = view.findViewById(R.id.btn_logout)
        txtEmail = view.findViewById(R.id.txtemail)
        txtName = view.findViewById(R.id.txtname)
        txtNoHp = view.findViewById(R.id.txtnohp)
        llkebijakanprivasi = view.findViewById(R.id.llkebijakanprivasi)
        llhubungikami = view.findViewById(R.id.llhubungikami)
        llketentuanlayanan = view.findViewById(R.id.llketentuanlayanan)
        llnilaikami = view.findViewById(R.id.llnilaikami)
        list1 = view.findViewById(R.id.list1)
        list2 = view.findViewById(R.id.list2)
        list3 = view.findViewById(R.id.list3)
    }
}