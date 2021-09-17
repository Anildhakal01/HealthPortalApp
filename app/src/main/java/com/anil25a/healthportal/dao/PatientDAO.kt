package com.anil25a.healthportal.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anil25a.healthportal.entity.Patient

@Dao
interface PatientDAO {
    //insert function
    @Insert
     fun registerPatient(patient: Patient)
    @Query("select * from Patient where email=(:email) and password=(:password)")
     fun checkPatient(email:String,password:String):Patient


}