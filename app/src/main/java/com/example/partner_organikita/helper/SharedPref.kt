package com.example.partner_organikita.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.partner_organikita.model.Store
import com.google.gson.Gson

class SharedPref(activity: Activity) {
    val login = "login"
    val storeEmail = "storeEmail"
    val storePassword = "storePassword"
    val store = "store"

    val mypref = "MAIN_PRF"
    val sp:SharedPreferences

    init {
        sp = activity.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status:Boolean){
        sp.edit().putBoolean(login, status).apply()
    }

    fun getStatusLogin() :Boolean{
        return sp.getBoolean(login, false)
    }

    fun setUser(value: Store){
        val data: String = Gson().toJson(value, Store::class.java)
        sp.edit().putString(store, data).apply()
    }

    fun getUser(): Store?{
        val data: String = sp.getString(store, null) ?: return null
        return Gson().fromJson<Store>(data, Store::class.java)
    }

    fun setString(key: String, value: String){
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return sp.getString(key, "")!!
    }
}