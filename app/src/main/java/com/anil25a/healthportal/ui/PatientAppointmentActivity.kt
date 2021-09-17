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
import com.anil25a.healthportal.adapter.AlertAdapter
import com.anil25a.healthportal.repository.PatientRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PatientAppointmentActivity : AppCompatActivity() {
    private lateinit var appointmentView : RecyclerView

    private lateinit var btnHome: Button
    private lateinit var btnHospital: Button
    private lateinit var btnAppointment: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_appointment)

        appointmentView = findViewById(R.id.appointmentView)
        btnHome = findViewById(R.id.ibtnHome)
        btnAppointment = findViewById(R.id.ibtnAppointment)
        btnHospital = findViewById(R.id.ibtnHospital)
        loadAppointmentAlerts()
        btnHome.setOnClickListener {
            startActivity(Intent(this@PatientAppointmentActivity, HomeActivity::class.java))
        }
        btnHospital.setOnClickListener {
            startActivity(Intent(this@PatientAppointmentActivity, DashboardActivity::class.java))
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
    private fun loadAppointmentAlerts(){
        val sharedPref = getSharedPreferences("LogData", MODE_PRIVATE)
        val patientId = sharedPref.getString("_id","")!!
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val patientRepository = PatientRepository()
                val response = patientRepository.viewAppointmentByPatient(patientId!!)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val appointment = response.data
                        appointmentView.addItemDecoration(DividerDecoration(10,10))
                        val adapter = AlertAdapter(this@PatientAppointmentActivity, appointment!!)
                        appointmentView.layoutManager = LinearLayoutManager(this@PatientAppointmentActivity)
                        appointmentView.adapter = adapter
                    }
                } else {

                    Toast.makeText(this@PatientAppointmentActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (ex:Exception){
            Toast.makeText(this@PatientAppointmentActivity, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
        }

    }
}