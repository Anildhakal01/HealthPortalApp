package com.anil25a.healthportal.repository

import com.anil25a.healthportal.api.MyAPIRequest
import com.anil25a.healthportal.api.ServiceBuilder
import com.anil25a.healthportal.entity.Patient

class PatientRepository :
    MyAPIRequest() {
//        val patientAPI = ServiceBuilder.buildService(PatientAPI::class.java)
//        suspend fun registerUser(name:String,dob:String,gender:String,address:String,phone:String,
//                                 email:String,password:String): Patient {
//            return apiRequest {
//                patientAPI.registerUser(name,dob,gender,address,phone,email,password)
//            }
//        }
//        suspend fun checkUser(email: String, password: String): LoginResponse {
//            return apiRequest {
//                userAPI.checkUser(email, password)
//            }
//        }
//        suspend fun viewUsers(): ViewAllUsersResponse {
//            return apiRequest {
//                userAPI.viewAllUsers()
//            }
//        }
//        suspend fun updateUsers(users:User): UpdateUsersResponse {
//            return apiRequest {
//                userAPI.updateUsers(users)
//            }
//        }
//        suspend fun deleteUserbyId(id:String): DeleteUserResponse {
//            return apiRequest {
//                userAPI.deleteUserbyId(id)
//            }
//        }
}