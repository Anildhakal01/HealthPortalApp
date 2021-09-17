package com.anil25a.healthportal.repository
import com.anil25a.healthportal.api.HospitalAPI
import com.anil25a.healthportal.api.MyAPIRequest
import com.anil25a.healthportal.api.ServiceBuilder
import com.anil25a.healthportal.entity.Hospital
import com.anil25a.healthportal.response.AddHospitalResponse
import com.anil25a.healthportal.response.HospitalLoginResponse
import com.anil25a.healthportal.response.UpdateHospitalResponse
import com.anil25a.healthportal.response.ViewAppointmentResponse

import okhttp3.MultipartBody
import okhttp3.RequestBody

class HospitalRepository :
    MyAPIRequest() {
    private lateinit var lstHospital:List<Hospital>
    val hospitalAPI = ServiceBuilder.buildService(HospitalAPI::class.java)
    suspend fun registerHospital(hname:RequestBody, desc:RequestBody, location:RequestBody,imgbody: MultipartBody.Part, email:RequestBody, password:RequestBody): AddHospitalResponse {
        return apiRequest {
            hospitalAPI.addHospital(hname,desc,location,imgbody,email,password)
        }
    }
    suspend fun logHospital(email: String, password: String): HospitalLoginResponse {
        return apiRequest {
            hospitalAPI.logHospital(email, password)
        }
    }
    suspend fun getAllHospital(): AddHospitalResponse {
        return apiRequest {
            hospitalAPI.getAllHospital()
        }
    }
    suspend fun getNearbyHospital(): AddHospitalResponse {
        return apiRequest {
            hospitalAPI.getNearbyHospital()
        }
    }
    suspend fun viewAppointment(_id:String): ViewAppointmentResponse {
        return apiRequest {
            hospitalAPI.viewAppointment(_id)
        }
    }

    suspend fun verifyAppointment(patientId:String,hospitalId:String): UpdateHospitalResponse {
        return apiRequest {
            hospitalAPI.verifyAppointment(patientId,hospitalId)
        }
    }
    suspend fun rejectAppointment(patientId:String,hospitalId:String): UpdateHospitalResponse {
        return apiRequest {
            hospitalAPI.rejectAppointment(patientId,hospitalId)
        }
    }
    suspend fun updateHospital(id:String,hname:RequestBody, desc:RequestBody, location:RequestBody,imgbody: MultipartBody.Part, email:RequestBody): UpdateHospitalResponse {
        return apiRequest {
            hospitalAPI.updateHospital(id,hname,desc,location,imgbody,email)
        }
    }
//    suspend fun insertHRoom(){
//        val response=getAllHospital()
//        lstHospital=response.data!!
//           db.getHospitalDAO().registerHospital(lstHospital)
//    }
    suspend fun deleteHospital(id: String): HospitalLoginResponse {
        return apiRequest {
            hospitalAPI.deleteHospital(id)
        }
}
}