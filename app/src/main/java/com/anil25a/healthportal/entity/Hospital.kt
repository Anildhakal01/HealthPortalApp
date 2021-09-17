package com.anil25a.healthportal.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hospital(
    @PrimaryKey
         var _id: String ="",
        var name: String? = null,
        var desc: String? = null,
        var location: String? = null,
        var himage:String?=null,
        var email: String? = null,
        var password: String? = null
    )