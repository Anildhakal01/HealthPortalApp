package com.anil25a.healthportal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Appointment(
    @PrimaryKey
    var _id: String ="",
    var patientId:Patient?=null,
    var hospitalId:String?=null,
    var issue: String? = null,
    var doctorName: String? = null,
    var dateTime:String?=null,
    var requestStatus:String=""
)
