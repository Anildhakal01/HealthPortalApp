package com.anil25a.healthportal.response

import com.anil25a.healthportal.entity.Hospital

data class HospitalLoginResponse(
    val success:Boolean?=null,
    val data:Hospital?=null
)
