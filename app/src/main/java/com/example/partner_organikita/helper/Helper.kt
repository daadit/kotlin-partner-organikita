package com.example.front_startup.helper

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    fun changeCurrency(string: String): String{
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(string))
    }

    fun changeCurrency(value: Int): String{
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(value)
    }

    fun convertTanggal(tgl: String, formatBaru: String, formatLama: String = "yyyy-MM-dd kk:mm:ss") :String{
        val dateFormat = SimpleDateFormat(formatLama)
        val convert = dateFormat.parse(tgl)
        dateFormat.applyPattern(formatBaru)
        return dateFormat.format(convert)
    }

    fun convertTanggalTwo(tgl: String, formatBaru: String, formatLama: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") :String{
        val dateFormat = SimpleDateFormat(formatLama)
        val convert = dateFormat.parse(tgl)
        dateFormat.applyPattern(formatBaru)
        return dateFormat.format(convert)
    }
}