package com.anil25a.healthportal.repository

import com.anil25a.healthportal.api.MyAPIRequest
import com.anil25a.healthportal.api.PatientAPI
import com.anil25a.healthportal.api.ServiceBuilder
import com.anil25a.healthportal.entity.Patient
import com.anil25a.healthportal.response.PatientLoginResponse
import com.anil25a.healthportal.response.RegisterPatientResponse
import com.anil25a.healthportal.response.ViewAppointmentResponse
import com.anil25a.healthportal.response.ViewPatientResponse

class PatientRepository :
    MyAPIRequest() {
        val patientAPI = ServiceBuilder.buildService(PatientAPI::class.java)
        suspend fun registerPatient(fname:String,lname:String,age:Number,gender:String,address:String,
                                 email:String,password:String): RegisterPatientResponse {
            return apiRequest {
                patientAPI.registerPatient(fname, lname, age, gender, address, email, password)
            }
        }
        suspend fun checkPatient(email: String, password: String): PatientLoginResponse {
            return apiRequest {
                patientAPI.checkPatient(email, password)
            }
        }
        suspend fun viewAllPatients(): ViewPatientResponse {
            return apiRequest {
                patientAPI.viewAllPatients()
            }
        }
        suspend fun updatePatient(_id:String,fname:String,lname:String,age:Number,gender:String,address:String,
                                  email:String): RegisterPatientResponse {
            return apiRequest {
                patientAPI.updatePatient(_id,fname, lname, age, gender, address, email)
            }
        }
        suspend fun deletePatientById(id:String): RegisterPatientResponse {
            return apiRequest {
                patientAPI.deletePatientById(id)
            }
        }
    suspend fun requestAppointment(hospitalId:String,patientId:String,issue:String,doctorName:String,
                                dateTime:String): RegisterPatientResponse {
        return apiRequest {
            patientAPI.requestAppointment(hospitalId,patientId,issue,doctorName,dateTime)
        }
    }
    suspend fun viewAppointmentByPatient(patientId:String): ViewAppointmentResponse {
        return apiRequest {
            patientAPI.viewAppointmentByPatient(patientId)
        }
    }
}