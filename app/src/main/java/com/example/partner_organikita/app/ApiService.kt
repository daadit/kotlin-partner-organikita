package com.example.partner_organikita.app

import com.example.partner_organikita.model.ProductModel
import com.example.partner_organikita.model.ResponseModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("partner-login")
    fun login(
        @Field("email") email:String,
        @Field("password") password :String,
    ):Call<ResponseModel>

    @GET("partner-history/{id}")
    fun getHistory(
        @Path("id") id: Int
    ):Call<ResponseModel>

    @GET("partner-product/{id}")
    fun getProduct(
        @Path("id") id: Int
    ):Call<ResponseModel>

    @GET("product-category")
    fun getCategory():Call<ResponseModel>

    @GET("partner-history/{id}/{status}")
    fun getHistoryByStatus(
        @Path("id") id: Int,
        @Path("status") status: String
    ):Call<ResponseModel>

    @POST("partner-status/{id}")
    fun changeStatus(
        @Path("id") id: Int
    ):Call<ResponseModel>

    @POST("partner-delivery/{id}/{delivery}")
    fun changeDeliveryDetail(
        @Path("id") id: Int,
        @Path("delivery") delivery: Int
    ):Call<ResponseModel>

    @POST("product/save")
    fun saveProduct(
        @Body data: ProductModel,
    ):Call<ResponseModel>

    @Multipart
    @POST("product/saveimage")
    fun saveProductImage(
        @Path("id") id: Int,
        @Part image: MultipartBody.Part
    ):Call<ResponseModel>
}