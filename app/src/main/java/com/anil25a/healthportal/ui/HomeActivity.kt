package com.anil25a.healthportal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anil25a.healthportal.R
import com.anil25a.healthportal.adapter.HospitalAdapter
import com.anil25a.healthportal.repository.HospitalRepository
import com.anil25a.healthportal.ui.admin.HospitalAdminActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {
    private lateinit var hospitalview : RecyclerView
    private lateinit var doctorView : RecyclerView
    private lateinit var tvWelcome: TextView
    private lateinit var btnHome: Button
    private lateinit var btnHospital: Button
    private lateinit var btnAppointment: Button
    private lateinit var tvDoctor:TextView
    private lateinit var tvHospital:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        hospitalview = findViewById(R.id.hospitalview)
        doctorView = findViewById(R.id.doctorView)
        tvWelcome = findViewById(R.id.tvWelcome)
        btnHome = findViewById(R.id.ibtnHome)
        btnAppointment = findViewById(R.id.ibtnAppointment)
        btnHospital = findViewById(R.id.ibtnHospital)
        tvHospital=findViewById(R.id.tvlHospital)
        tvDoctor=findViewById(R.id.tvlNearby)
        val sharedPref = getSharedPreferences("LogData",MODE_PRIVATE)
        val name = sharedPref.getString("name", "")
        val type = sharedPref.getString("type", "")
        tvWelcome.setText("Welcome $name")
        loadHospital()
        loadNearbyHospitals()
        tvHospital.setOnClickListener {
            startActivity(Intent(this@HomeActivity, DashboardActivity::class.java))
        }
        tvDoctor.setOnClickListener {
            startActivity(Intent(this@HomeActivity, DashboardActivity::class.java))
        }
        btnHospital.setOnClickListener {
            startActivity(Intent(this@HomeActivity, DashboardActivity::class.java))
        }
        btnAppointment.setOnClickListener {
            if(type=="Hospital")
            {
                startActivity(Intent(this@HomeActivity, VerifyAppointmentActivity::class.java))
            }
            else{
                startActivity(Intent(this@HomeActivity, PatientAppointmentActivity::class.java))
            }

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itLogout -> removeSharedPref()
        }
        return true
    }
    private fun removeSharedPref(){
        val sharedpref = getSharedPreferences("LogData",MODE_PRIVATE)
        val editor = sharedpref.edit()
        editor.clear()
        editor.commit()
        finishAffinity()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)

    }
    private fun loadHospital(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val hospitalRepository = HospitalRepository()
                val response = hospitalRepository.getAllHospital()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val hospital = response.data
                        hospitalview.addItemDecoration(DividerDecoration(5,5))
                        val adapter = HospitalAdapter(this@HomeActivity, hospital!!,"dummy")
                        hospitalview.layoutManager = LinearLayoutManager(this@HomeActivity,LinearLayoutManager.HORIZONTAL,false)
                        hospitalview.adapter = adapter
                    }

                } else {

                    Toast.makeText(this@HomeActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (ex:Exception){
            Toast.makeText(this@HomeActivity, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
        }

    }
    private fun loadNearbyHospitals(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val hospitalRepository = HospitalRepository()
                val response = hospitalRepository.getNearbyHospital()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val hospital = response.data
                        doctorView.addItemDecoration(DividerDecoration(10,10))
                        val adapter = HospitalAdapter(this@HomeActivity, hospital!!,"dummy")
                        doctorView.layoutManager = LinearLayoutManager(this@HomeActivity,LinearLayoutManager.HORIZONTAL,false)
                        doctorView.adapter = adapter
                    }

                } else {

                    Toast.makeText(this@HomeActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (ex:Exception){
            Toast.makeText(this@HomeActivity, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
        }

    }
}