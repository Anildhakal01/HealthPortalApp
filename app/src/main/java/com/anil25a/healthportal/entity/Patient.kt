package com.anil25a.healthportal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Patient(
    var fname:String?=null,
    var lname:String?=null,
    var age:Int?=null,
    var gender:String?=null,
    var address:String?=null,
    var email:String?=null,
    var password:String?=null
) {
    @PrimaryKey(autoGenerate = true)
    var userId:Int=0
}