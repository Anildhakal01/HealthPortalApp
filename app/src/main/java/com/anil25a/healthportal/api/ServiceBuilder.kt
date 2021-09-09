package com.anil25a.healthportal.api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL="http://10.0.2.2:2000/"
    var token:String?=null
    //network protocol like http
    private val okHttp= OkHttpClient.Builder()
    //building retrofit2
    //retrofit for communication between two
    private val retrofitBuilder= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
    //Create retrofit instance
    private val retrofit= retrofitBuilder.build()
    //Generic function
    fun<T>buildService(serviceType:Class<T>):T{
        return retrofit.create(serviceType)
    }
    fun loadImagePath(): String {
        val arr = BASE_URL.split("/").toTypedArray()
        return arr[0] + "/" + arr[1] + arr[2] + "/"
    }

}