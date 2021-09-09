package com.anil25a.healthportal.api
import com.anil25a.healthportal.response.AddHospitalResponse
import com.anil25a.healthportal.response.DeleteHospitalResponse
import com.anil25a.healthportal.response.HospitalLoginResponse
import com.anil25a.healthportal.response.UpdateHospitalResponse
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

    @GET("hospital/{email}")
    suspend fun viewPropertyById(
        @Path("email") email: String
    ): Response<AddHospitalResponse>
    @DELETE("property/{id}")
    suspend fun deleteHospital(
        @Path("id") id: String
    ): Response<DeleteHospitalResponse>

    @Multipart
    @PUT("/updateHospital/{id}")
    suspend fun updateHospital(
        @Path("id")id:String,
        @Part("property_name")property_name: RequestBody,
        @Part("price")price:RequestBody,
        @Part("property_status")property_status:RequestBody,
        @Part("property_type")property_type:RequestBody,
        @Part("address")address:RequestBody,
        @Part("Distance_between_road")Distance_between_road :RequestBody,
        @Part("property_area")property_area:RequestBody,
        @Part("description")description:RequestBody,
        @Part file: MultipartBody.Part,
        @Part("email")email:RequestBody,

        ): Response<UpdateHospitalResponse>


}