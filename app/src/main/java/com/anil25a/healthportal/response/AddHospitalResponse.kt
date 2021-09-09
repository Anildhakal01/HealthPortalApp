package com.anil25a.healthportal.response

import com.anil25a.healthportal.entity.Hospital

data class AddHospitalResponse (
    val success:Boolean?=null,
    val data: MutableList<Hospital>?=null
)
