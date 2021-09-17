package com.anil25a.healthportal.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anil25a.healthportal.R
import com.anil25a.healthportal.adapter.HospitalAdapter
import com.anil25a.healthportal.adapter.HospitalAdminAdapter
import com.anil25a.healthportal.db.HealthPortalDatabase
import com.anil25a.healthportal.repository.HospitalRepository
import com.anil25a.healthportal.ui.DividerDecoration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HospitalAdminActivity : AppCompatActivity() {
    private lateinit var hospitalview : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_admin)
        hospitalview=findViewById(R.id.hospitalAdminView)
        loadHospital()
    }
private fun loadHospital(){
    try {
        CoroutineScope(Dispatchers.IO).launch {
            val hospitalRepository = HospitalRepository()
            val response = hospitalRepository.getAllHospital()
            if (response.success == true) {
                withContext(Dispatchers.Main) {
                    val hospital = response.data
                    hospitalview.addItemDecoration(DividerDecoration(10,10))
                    val adapter = HospitalAdminAdapter(this@HospitalAdminActivity, hospital!!)
                    hospitalview.layoutManager = LinearLayoutManager(this@HospitalAdminActivity)
                    hospitalview.adapter = adapter
                }

            } else {

                Toast.makeText(this@HospitalAdminActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    catch (ex:Exception){
//                withContext(Dispatchers.Main){
        Toast.makeText(this@HospitalAdminActivity, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
//                }
    }

}
    }