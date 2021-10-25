package com.example.partner_organikita

import android.content.BroadcastReceiver
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.partner_organikita.fragment.HomeFragment
import com.example.partner_organikita.fragment.ProductFragment
import com.example.partner_organikita.fragment.TransaksiFragment
import com.example.partner_organikita.fragment.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.content.Intent
import android.content.IntentFilter
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {
    var doubleBackToExitPressedOnce = false

    private var beranda :Boolean = false
    private var transaksi :Boolean = false
    private var produk :Boolean = false
    private var profil :Boolean = false

    private val homeFragment: Fragment = HomeFragment()
    private val transaksiFragment: Fragment = TransaksiFragment()
    private val productFragment: Fragment = ProductFragment()
    private val userFragment: Fragment = UserFragment()

    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = homeFragment

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.botNav)

        window.setStatusBarColor(ContextCompat.getColor(this@MainActivity,R.color.white))

        setNav()
        isOnline(this)

        LocalBroadcastManager.getInstance(this).registerReceiver(message, IntentFilter("event:beranda"))
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessage, IntentFilter("event:transaksi"))
        LocalBroadcastManager.getInstance(this).registerReceiver(mmMessage, IntentFilter("event:produk"))
        LocalBroadcastManager.getInstance(this).registerReceiver(mmmMessage, IntentFilter("event:profil"))
    }

    val message : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            beranda = true
        }
    }

    val mMessage : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            transaksi = true
        }
    }

    val mmMessage : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            produk = true
        }
    }

    val mmmMessage : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            profil = true
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        val bottomSheetDialog = BottomSheetDialog(
            this@MainActivity, R.style.BottomSheetDialogTheme
        )
        val bottomSheetView = LayoutInflater.from(this@MainActivity).inflate(
            R.layout.layout_sheet_koneksi,
            findViewById(R.id.bottomSheet)
        )

        bottomSheetView.findViewById<Button>(R.id.btnclosekoneksi)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<Button>(R.id.btnnantikoneksi)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<Button>(R.id.btnpengaturan)?.setOnClickListener {
            bottomSheetDialog.dismiss()
            startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
        return false
    }

    fun setNav(){

        fm.beginTransaction().add(R.id.place, homeFragment).show(homeFragment).commit()
        fm.beginTransaction().add(R.id.place, transaksiFragment).hide(transaksiFragment).commit()
        fm.beginTransaction().add(R.id.place, productFragment).hide(productFragment).commit()
        fm.beginTransaction().add(R.id.place, userFragment).hide(userFragment).commit()

        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.beranda -> {
                    callFragment(0, homeFragment)
                }
                R.id.transaksi -> {
                    callFragment(1, transaksiFragment)
                }
                R.id.produk -> {
                    callFragment(2, productFragment)
                }
                R.id.profil -> {
                    callFragment(3, userFragment)
                }
            }
            true
        }
    }

    fun callFragment(int: Int, fragment: Fragment){
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onResume() {
        if (beranda) {
            beranda = false
            callFragment(0, homeFragment)
        }
        if (transaksi) {
            transaksi = false
            callFragment(1, transaksiFragment)
        }
        if (produk) {
            produk = false
            callFragment(2, productFragment)
        }
        if (profil) {
            profil = false
            callFragment(3, userFragment)
        }
        super.onResume()
    }
}