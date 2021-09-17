package com.anil25a.healthportal.response

import com.anil25a.healthportal.entity.Appointment

data class ViewAppointmentResponse(
    val success:Boolean?=null,
    val data: MutableList<Appointment>?=null
)
