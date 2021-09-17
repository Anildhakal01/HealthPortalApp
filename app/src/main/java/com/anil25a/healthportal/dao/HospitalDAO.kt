package com.anil25a.healthportal.dao
import androidx.room.Dao
import androidx.room.Delete
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
//    @Delete
//    suspend fun deleteHospitalById(hospital: Hospital)
//
//    @Query("select * from Hospital")
//    suspend fun viewHospital():MutableList<Hospital>

}