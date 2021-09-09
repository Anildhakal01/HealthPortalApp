package com.anil25a.wearos.apis

import com.anil25a.wearos.response.HospitalLoginResponse
import retrofit2.Response
import retrofit2.http.*

interface HospitalAPI {
    @FormUrlEncoded
    @POST("hospital/login")
    suspend fun logHospital(
        @Field("email")email:String,
        @Field("password")password:String
    ):Response<HospitalLoginResponse>

}