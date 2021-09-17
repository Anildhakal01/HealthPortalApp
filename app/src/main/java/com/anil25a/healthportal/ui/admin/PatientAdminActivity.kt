package com.anil25a.healthportal.ui.admin


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anil25a.healthportal.R
import com.anil25a.healthportal.adapter.PatientAdapter
import com.anil25a.healthportal.db.HealthPortalDatabase
import com.anil25a.healthportal.repository.PatientRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PatientAdminActivity : AppCompatActivity() {
    private lateinit var patientView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_admin)
        patientView=findViewById(R.id.patientAdminView)
        loadUsers()
    }
    private fun loadUsers(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val patientrepo = PatientRepository()
                val response = patientrepo.viewAllPatients()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val patientData = response.data
//                        HealthPortalDatabase.getInstance(this@PatientAdminActivity).getUserDAO().registerPatient(user_data!!)
//                        val usersdata= HealthPortalDatabase
//                                .getInstance(this@PatientAdminActivity)
//                                .getUserDAO()
//                                .viewAllUsers()
                        val adapter = PatientAdapter(this@PatientAdminActivity, patientData!!)
                        patientView.layoutManager = LinearLayoutManager(this@PatientAdminActivity)
                        patientView.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@PatientAdminActivity, "Not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (ex:Exception){
            Toast.makeText(this@PatientAdminActivity, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
        }
    }
}