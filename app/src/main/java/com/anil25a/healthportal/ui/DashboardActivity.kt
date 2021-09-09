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
import com.anil25a.healthportal.db.database
import com.anil25a.healthportal.repository.HospitalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity : AppCompatActivity() {
    private lateinit var hospitalview : RecyclerView
    private lateinit var tvWelcome: TextView
    private lateinit var btnHome: Button
    private lateinit var btnHospital: Button
    private lateinit var btnDoctors: Button
    private lateinit var btnUserAc: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvWelcome = findViewById(R.id.tvWelcome)
        hospitalview = findViewById(R.id.hospitalview)
        btnHome = findViewById(R.id.ibtnHome)
        btnDoctors = findViewById(R.id.ibtnDoctor)
        btnHospital = findViewById(R.id.ibtnHospital)
        btnUserAc = findViewById(R.id.ibtnUserAccount)
            loadHospital()
        var msg= intent.getStringExtra("Email")

        tvWelcome.setText("Welcome $msg")
        btnUserAc.setOnClickListener {
            removeSharedPref()
        }


    }
    private fun removeSharedPref(){
        val sharedpref = getSharedPreferences("LoggedinPref",MODE_PRIVATE)
        val editor = sharedpref.edit()
        editor.clear()
        editor.commit()
        val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }
    private fun loadHospital(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
//            val db= database.getInstance(this@DashboardActivity)
                val hospitalRepository = HospitalRepository()
                val response = hospitalRepository.getAllHospital()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val hospital = response.data
                        hospitalview.addItemDecoration(DividerDecoration(10,10))
                        val adapter = HospitalAdapter(this@DashboardActivity, hospital!!)
                        hospitalview.layoutManager = LinearLayoutManager(this@DashboardActivity)
                        hospitalview.adapter = adapter
                        val m: String = ""
                        Toast.makeText(this@DashboardActivity, "dd", Toast.LENGTH_SHORT).show()
                    }

                } else {

                    Toast.makeText(this@DashboardActivity, "", Toast.LENGTH_SHORT).show()
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