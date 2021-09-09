package com.anil25a.wearos.repos

import com.anil25a.wearos.apis.HospitalAPI
import com.anil25a.wearos.apis.MyAPIRequest
import com.anil25a.wearos.apis.ServiceBuilder
import com.anil25a.wearos.response.HospitalLoginResponse
class HospitalRepository :
    MyAPIRequest() {
    val hospitalAPI = ServiceBuilder.buildService(HospitalAPI::class.java)

     suspend fun logHospital(email: String, password: String): HospitalLoginResponse {
        return apiRequest {
            hospitalAPI.logHospital(email, password)
        }
    }
}