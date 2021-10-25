package com.example.partner_organikita.model

class DetailTransactionModel {
    var detailTotalItem = 0
    var detailTotalPrice = 0
    var product = Item()

    class Item{
        var productId = 0
        lateinit var productName: String
        lateinit var productDescription: String
        var productStore = 0
        var productCategory = 0
        var productPrice = 0
        lateinit var productImage: String
        var productStatus = 0
        var productRating = ""
        var productSold = ""
        lateinit var productCreatedAt: String

        var store = Itemtwo()
    }

    class Itemtwo{
        var storeId = 0
        var storeName = ""
        var storeEmail = ""
        var storePhoneNumber = ""
        var storeStreet = ""
        var storeDistrict = ""
        var storeCity = ""
        var storeProvince = ""
        var storeZipCode = ""
    }
}