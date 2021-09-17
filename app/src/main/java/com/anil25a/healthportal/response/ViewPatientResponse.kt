package com.anil25a.healthportal.response

import com.anil25a.healthportal.entity.Patient

data class ViewPatientResponse(
    val success:Boolean?=null,
    val data: MutableList<Patient>?=null
)
