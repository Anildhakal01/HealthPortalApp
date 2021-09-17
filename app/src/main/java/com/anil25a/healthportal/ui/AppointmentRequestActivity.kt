package com.anil25a.healthportal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import com.anil25a.healthportal.R
import com.anil25a.healthportal.repository.PatientRepository
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class AppointmentRequestActivity : AppCompatActivity() {
    private lateinit var ivImageView:ImageView
    private lateinit var tvHname:TextView
    private lateinit var tvILocation:TextView
    private lateinit var tvIDescription:TextView
    private lateinit var etIssue:TextInputEditText
    private lateinit var etDoctor:TextInputEditText
    private lateinit var etDateTime:EditText
    private lateinit var btnSend:Button
    private lateinit var tvContact:TextView

    private lateinit var btnBack: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_request)

        btnBack = findViewById(R.id.btnBack)
        ivImageView=findViewById(R.id.ivImageView)
        tvHname=findViewById(R.id.tvHname)
        tvILocation=findViewById(R.id.tvILocation)
        tvIDescription=findViewById(R.id.tvIDescription)
        etIssue=findViewById(R.id.etIssue)
        etDoctor=findViewById(R.id.etDoctor)
        etDateTime=findViewById(R.id.etDateTime)
        tvContact=findViewById(R.id.tvContact)
        btnSend=findViewById(R.id.btnSend)
        val sharedPref = getSharedPreferences("LogData", MODE_PRIVATE)
        val type = sharedPref.getString("type","")
        if(type=="Hospital"){
            tvContact.isVisible=false
            etDateTime.isVisible=false
            etDoctor.isVisible=false
            etIssue.isVisible=false
            btnSend.isVisible=false
        }
        val imagePath=(intent.getStringExtra("imagepath"))
        Glide.with(this@AppointmentRequestActivity)
            .load(imagePath)
            .fitCenter()
            .into(ivImageView)
        tvHname.setText(intent.getStringExtra("name"))
        tvILocation.setText(intent.getStringExtra("location"))
        tvIDescription.setText(intent.getStringExtra("desc"))
        btnSend.setOnClickListener {
            requestAppointment()
        }
        btnBack.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this@AppointmentRequestActivity, HomeActivity::class.java))
        }

    }
    private fun requestAppointment(){
        var issue = etIssue.text.toString()
        var doctorName = etDoctor.text.toString()
        var dateTime = etDateTime.text.toString()
        val hospitalId=intent.getStringExtra("hospitalId")
        val sharedPref = getSharedPreferences("LogData", MODE_PRIVATE)
        val patientId = sharedPref.getString("_id","")
            CoroutineScope(Dispatchers.IO).launch {
                val repository = PatientRepository()
                val response = repository.requestAppointment(
                    hospitalId=hospitalId!!,
                    patientId=patientId!!,
                    issue=issue,
                    doctorName = doctorName,
                    dateTime=dateTime,
                )
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AppointmentRequestActivity,
                            "Appointment Request Sent",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AppointmentRequestActivity,
                            "Registration Error",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
            }

    }

}