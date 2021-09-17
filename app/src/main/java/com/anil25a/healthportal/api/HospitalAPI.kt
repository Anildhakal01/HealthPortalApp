package com.anil25a.healthportal.api
import com.anil25a.healthportal.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface HospitalAPI {
    @POST("hospital/add")
    @Multipart
    suspend fun addHospital(
    @Part("hname")hname:RequestBody,
    @Part("desc")desc:RequestBody,
    @Part("location")location:RequestBody,
    @Part file: MultipartBody.Part,
    @Part("email")email:RequestBody,
    @Part("password")password:RequestBody
    ): Response<AddHospitalResponse>

    @FormUrlEncoded
    @POST("hospital/login")
    suspend fun logHospital(
        @Field("email")email:String,
        @Field("password")password:String
    ):Response<HospitalLoginResponse>

    @GET("hospital/showall")
    suspend fun getAllHospital(
    ):Response<AddHospitalResponse>


    @GET("hospital/nearby")
    suspend fun getNearbyHospital(
    ):Response<AddHospitalResponse>

    @GET("viewAppointments/{id}")
    suspend fun viewAppointment(
        @Path("id") id: String
    ): Response<ViewAppointmentResponse>
    @DELETE("hospital/delete/{id}")
    suspend fun deleteHospital(
        @Path("id") id: String
    ): Response<HospitalLoginResponse>

    @Multipart
    @PUT("/hospital/update/{id}")
    suspend fun updateHospital(
        @Path("id")id:String,
        @Part("name")hname:RequestBody,
        @Part("desc")desc:RequestBody,
        @Part("location")location:RequestBody,
        @Part file: MultipartBody.Part,
        @Part("email")email:RequestBody,
        ): Response<UpdateHospitalResponse>

    @FormUrlEncoded
    @PUT("/hospital/verifyAppointment")
    suspend fun verifyAppointment(
        @Field("patientId")patientId:String,
        @Field("hospitalId")hospitalId:String,
    ): Response<UpdateHospitalResponse>

    @FormUrlEncoded
    @PUT("/hospital/rejectAppointment")
    suspend fun rejectAppointment(
        @Field("patientId")patientId:String,
        @Field("hospitalId")hospitalId:String,
    ): Response<UpdateHospitalResponse>

}