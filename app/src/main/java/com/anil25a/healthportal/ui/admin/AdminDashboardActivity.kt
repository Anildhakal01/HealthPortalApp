package com.anil25a.healthportal.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.anil25a.healthportal.R
import com.anil25a.healthportal.ui.LoginActivity

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var btnHospital:Button
    private lateinit var btnPatient:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)
        btnHospital=findViewById(R.id.btnHospital)
        btnPatient=findViewById(R.id.btnPatient)
        btnHospital.setOnClickListener {
            startActivity(Intent(this@AdminDashboardActivity,HospitalAdminActivity::class.java))
        }
        btnPatient.setOnClickListener {
            startActivity(Intent(this@AdminDashboardActivity,PatientAdminActivity::class.java))
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.itAccount ->startActivity(Intent(this@DashboardActivity,LoginActivity::class.java))
            R.id.itLogout ->startActivity(Intent(this@AdminDashboardActivity, LoginActivity::class.java))
        }
        return true
    }
}