package com.anil25a.healthportal.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anil25a.healthportal.entity.Hospital

@Dao
interface HospitalDAO {
    //insert function
    @Insert
     fun registerHospital(hospital: MutableList<Hospital>)
    //read/login function
//    @Query("select * from Hospital where email=(:email) and password=(:password)")
//    suspend fun checkHospital(email:String,password:String): Hospital



}