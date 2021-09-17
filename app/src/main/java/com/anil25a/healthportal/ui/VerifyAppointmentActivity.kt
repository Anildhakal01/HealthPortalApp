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
import com.anil25a.healthportal.adapter.AppointmentAdapter
import com.anil25a.healthportal.adapter.HospitalAdapter
import com.anil25a.healthportal.repository.HospitalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VerifyAppointmentActivity : AppCompatActivity() {
    private lateinit var hospitalview : RecyclerView

    private lateinit var btnHome: Button
    private lateinit var btnHospital: Button
    private lateinit var btnAppointment: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_appointment)


        hospitalview = findViewById(R.id.hospitalview)
        btnHome = findViewById(R.id.ibtnHome)
        btnAppointment = findViewById(R.id.ibtnAppointment)
        btnHospital = findViewById(R.id.ibtnHospital)
        loadAppointment()
        var msg= intent.getStringExtra("Email")

        btnHome.setOnClickListener {
            startActivity(Intent(this@VerifyAppointmentActivity, HomeActivity::class.java))
        }
        btnHospital.setOnClickListener {
            startActivity(Intent(this@VerifyAppointmentActivity, DashboardActivity::class.java))
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
    private fun loadAppointment(){
        val sharedPref = getSharedPreferences("LogData", MODE_PRIVATE)
        val _id = sharedPref.getString("_id","")!!
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val hospitalRepository = HospitalRepository()
                val response = hospitalRepository.viewAppointment(_id)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val appointment = response.data
                        hospitalview.addItemDecoration(DividerDecoration(10,10))
                        val adapter = AppointmentAdapter(this@VerifyAppointmentActivity, appointment!!,_id)
                        hospitalview.layoutManager = LinearLayoutManager(this@VerifyAppointmentActivity)
                        hospitalview.adapter = adapter
                        val m: String = ""
                    }

                } else {

                    Toast.makeText(this@VerifyAppointmentActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (ex:Exception){
//                withContext(Dispatchers.Main){
            Toast.makeText(this@VerifyAppointmentActivity, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
//                }
        }

    }
}