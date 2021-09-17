package com.anil25a.healthportal.api

import com.anil25a.healthportal.entity.Patient
import com.anil25a.healthportal.response.PatientLoginResponse
import com.anil25a.healthportal.response.RegisterPatientResponse
import com.anil25a.healthportal.response.ViewAppointmentResponse
import com.anil25a.healthportal.response.ViewPatientResponse
import retrofit2.Response
import retrofit2.http.*

interface PatientAPI {
    @FormUrlEncoded
    @POST("patient/add")
    suspend fun registerPatient(
        @Field("fname")fname:String,
        @Field("lname")lname:String,
        @Field("age")age:Number,
        @Field("gender")gender:String,
        @Field("address")address:String,
        @Field("email")email:String,
        @Field("password")password:String
    ): Response<RegisterPatientResponse>

    @FormUrlEncoded
    @POST("/patient/login")
    suspend fun checkPatient(
        @Field("email")email:String,
        @Field("password")password:String

    ): Response<PatientLoginResponse>

    @GET("patient/showall")
    suspend fun viewAllPatients(): Response<ViewPatientResponse>

    @GET("patient/viewAppointments/{id}")
    suspend fun viewAppointmentByPatient(
        @Path("id")id:String,
    ): Response<ViewAppointmentResponse>


    @PUT("/patient/update")
    suspend fun updatePatient(
        @Field("_id")_id:String,
        @Field("fname")fname:String,
        @Field("lname")lname:String,
        @Field("age")age:Number,
        @Field("gender")gender:String,
        @Field("address")address:String,
        @Field("email")email:String,
    ): Response<RegisterPatientResponse>

    @DELETE("/patient/delete/{id}")
    suspend fun deletePatientById(
        @Path("id") id:String
    ): Response<RegisterPatientResponse>

    @FormUrlEncoded
    @POST("requestAppointment")
    suspend fun requestAppointment(
        @Field("hospitalId")fname:String,
        @Field("patientId")lname:String,
        @Field("issue")issue:String,
        @Field("doctorName")doctorName:String,
        @Field("dateTime")dateTime:String,
    ): Response<RegisterPatientResponse>


}