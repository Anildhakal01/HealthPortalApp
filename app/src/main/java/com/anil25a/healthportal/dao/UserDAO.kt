package com.anil25a.healthportal.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anil25a.healthportal.entity.Patient

@Dao
interface UserDAO {
    //insert function
    @Insert
     fun registerUser(user: Patient)
    @Query("select * from Patient where email=(:email) and password=(:password)")
     fun checkUser(email:String,password:String):Patient


}