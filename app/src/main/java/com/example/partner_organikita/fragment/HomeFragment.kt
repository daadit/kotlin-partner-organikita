package com.example.partner_organikita.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.partner_organikita.R
import com.example.partner_organikita.adapter.AdapterImageSliderHome
import com.example.partner_organikita.adapter.AdapterTabLayoutHome
import com.example.partner_organikita.helper.SharedPref
import com.example.partner_organikita.model.ImageSliderHomeModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.net.URLEncoder
import kotlin.math.abs

class HomeFragment : Fragment() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager2: ViewPager2
    lateinit var layoutshimmerslider: ShimmerFrameLayout
    lateinit var view_pager: ViewPager2
    private val sliderHandler = Handler()
    lateinit var swipeToResfreh: SwipeRefreshLayout
    lateinit var s:SharedPref
    lateinit var txtName: TextView
    lateinit var btnhelp: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)

        s = SharedPref(requireActivity())

        Handler(Looper.getMainLooper()).postDelayed({
            imageSlider()
        },1500)

        tabLayoutTransaksi()
        refreshApp()
        setData()
        mainButton()
        return view
    }

    private fun mainButton(){
        btnhelp.setOnClickListener {
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

    private fun setData() {
        val user = s.getUser()!!

        txtName.text = user.storeName
    }

    private fun refreshApp(){
        swipeToResfreh.setOnRefreshListener {
            swipeToResfreh.isRefreshing = false
        }
    }

    private fun tabLayoutTransaksi(){
        val adapter = AdapterTabLayoutHome(childFragmentManager,lifecycle)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="Diproses"
                }
                1->{
                    tab.text="Dikirim"
                }
            }
        }.attach()

        viewPager2.setSaveEnabled(false)
    }

    private fun imageSlider(){
        layoutshimmerslider.stopShimmer()
        layoutshimmerslider.visibility = View.GONE
        view_pager.visibility = View.VISIBLE

        val sliderItems: MutableList<ImageSliderHomeModel> = ArrayList()
        sliderItems.add(ImageSliderHomeModel(R.drawable.slider))
        sliderItems.add(ImageSliderHomeModel(R.drawable.slider))
        sliderItems.add(ImageSliderHomeModel(R.drawable.slider))
        sliderItems.add(ImageSliderHomeModel(R.drawable.slider))

        view_pager.adapter = AdapterImageSliderHome(sliderItems, sliderItems, view_pager)

        view_pager.clipToPadding = false
        view_pager.clipChildren = false
        view_pager.offscreenPageLimit = 3
        view_pager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(25))
        compositePageTransformer.addTransformer { page, position ->
            val r:Float = 0 - abs(position)
            page.scaleY = 1f + r * 0.10f
        }

        view_pager.setPageTransformer(compositePageTransformer)

        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 5000)
            }
        })
    }

    private val sliderRunnable = Runnable {
        view_pager.currentItem = view_pager.currentItem + 1
    }

    fun init(view: View) {
        tabLayout = view.findViewById(R.id.tablayout)
        viewPager2 = view.findViewById(R.id.vpisi)
        layoutshimmerslider = view.findViewById(R.id.shimmerslider)
        view_pager = view.findViewById(R.id.vp2_slider)
        swipeToResfreh = view.findViewById(R.id.swipeToRefreshHome)
        txtName = view.findViewById(R.id.tvtoko)
        btnhelp = view.findViewById(R.id.btnhelp)
    }
}