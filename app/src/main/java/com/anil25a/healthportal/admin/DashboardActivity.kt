package com.anil25a.healthportal.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.anil25a.healthportal.R
//import com.miraj25a.hamroagent.ui.admin.PropertyAdminActivity
//import com.miraj25a.hamroagent.ui.admin.UsersAdminActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var btnProperties:Button
    private lateinit var btnUsers:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)
        btnProperties=findViewById(R.id.btnProperties)
        btnUsers=findViewById(R.id.btnUsers)
        btnProperties.setOnClickListener {
//            startActivity(Intent(this@DashboardActivity, PropertyAdminActivity::class.java))
        }
        btnUsers.setOnClickListener {
//            startActivity(Intent(this@DashboardActivity, UsersAdminActivity::class.java))
        }
    }
}