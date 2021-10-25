package com.example.partner_organikita.model

class ResponseModel {
    var success = 0
    lateinit var message: String
    var istheretransaction: Boolean = true
    var product:ArrayList<ProductModel> = ArrayList()
    var store = Store()
    var transaksis:ArrayList<TransactionModel> = ArrayList()
    var productcategory:ArrayList<ProductCategoryModel> = ArrayList()
}