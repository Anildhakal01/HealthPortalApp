package com.anil25a.healthportal.response

import com.anil25a.healthportal.entity.Patient

data class PatientLoginResponse(
    val success:Boolean?=null,
    val data:Patient?=null
)
