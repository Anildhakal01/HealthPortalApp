package com.anil25a.healthportal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anil25a.healthportal.R
import com.anil25a.healthportal.adapter.HospitalAdapter
import com.anil25a.healthportal.repository.HospitalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.SharedPreferences
import android.view.Menu
import android.view.MenuItem


class DashboardActivity : AppCompatActivity() {
    private lateinit var hospitalview : RecyclerView
    private lateinit var tvWelcome: TextView
    private lateinit var btnHome: Button
    private lateinit var btnHospital: Button
    private lateinit var btnAppointment: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvWelcome = findViewById(R.id.tvWelcome)
        hospitalview = findViewById(R.id.hospitalview)
        btnHome = findViewById(R.id.ibtnHome)
        btnAppointment = findViewById(R.id.ibtnAppointment)
        btnHospital = findViewById(R.id.ibtnHospital)
            loadHospital()

        btnHome.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, HomeActivity::class.java))
        }

        val sharedPref = getSharedPreferences("LogData",MODE_PRIVATE)
        val type = sharedPref.getString("type", "")
        btnAppointment.setOnClickListener {
            if(type=="Hospital")
            {
                startActivity(Intent(this@DashboardActivity, VerifyAppointmentActivity::class.java))
            }
            else{
                startActivity(Intent(this@DashboardActivity, PatientAppointmentActivity::class.java))
            }

        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.itAccount ->startActivity(Intent(this@DashboardActivity,LoginActivity::class.java))
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
            val sharedPref = getSharedPreferences("LogData", MODE_PRIVATE)
             val patientId = sharedPref.getString("_id","")!!
        try {
            CoroutineScope(Dispatchers.IO).launch {
//            val db= database.getInstance(this@DashboardActivity)
                val hospitalRepository = HospitalRepository()
                val response = hospitalRepository.getAllHospital()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val hospital = response.data
                        hospitalview.addItemDecoration(DividerDecoration(10,10))
                        val adapter = HospitalAdapter(this@DashboardActivity, hospital!!,patientId!!)
                        hospitalview.layoutManager = LinearLayoutManager(this@DashboardActivity)
                        hospitalview.adapter = adapter
                        val m: String = ""
                    }

                } else {

                    Toast.makeText(this@DashboardActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (ex:Exception){
//                withContext(Dispatchers.Main){
            Toast.makeText(this@DashboardActivity, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
//                }
        }

    }
}