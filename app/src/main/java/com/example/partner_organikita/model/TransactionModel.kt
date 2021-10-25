package com.example.partner_organikita.model

class TransactionModel {
    var transactionId = 0
    var id = 0
    var transactionBank = ""
    var transactionCostShipping = ""
    var transactionCourier = ""
    var transactionMethod = ""
    var transactionName = ""
    var transactionPhone = ""
    var transactionLocationDetail = ""
    var transactionDescription = ""
    var transactionTotalItem = ""
    var transactionTotalPrice = ""
    var transactionTotalTransfer = ""
    var transactionUserId = ""
    var transactionPaymentCode = ""
    var transactionCode = ""
    var transactionUniqueCode = 0
    var transactionStatus = ""
    var transactionExpiredAt = ""
    var updated_at = ""
    var created_at = ""
    var transactionCreatedAt = ""
    var transactionStore = 0
    var transactionDeliveryDetail = 0
    var customer = Item()
    val details = ArrayList<DetailTransactionModel>()

    class Item{
        lateinit var customerEmail: String
        lateinit var customerName: String
        lateinit var customerPhoneNumber: String
        lateinit var customerImage: String
        var customerId = 0
    }
}