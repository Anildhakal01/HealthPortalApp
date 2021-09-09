package com.anil25a.healthportal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.anil25a.healthportal.R
import com.anil25a.healthportal.db.database
import com.anil25a.healthportal.entity.Patient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PatientRegistrationActivity : AppCompatActivity() {
    private lateinit var etFname: EditText
    private lateinit var etLname: EditText
    private lateinit var etAge: EditText
    private lateinit var rdbMale: RadioButton
    private lateinit var rdbFemale: RadioButton
    private lateinit var rdbOthers: RadioButton
    private lateinit var etAddress: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirm: EditText
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_registration)
        etFname=findViewById(R.id.etFname)
        etLname=findViewById(R.id.etLname)
        etAge=findViewById(R.id.etAge)
        etAddress=findViewById(R.id.etAddress)
        rdbMale=findViewById(R.id.rdbMale)
        rdbFemale=findViewById(R.id.rdbFemale)
        rdbOthers=findViewById(R.id.rdbOthers)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        btnRegister=findViewById(R.id.btnRegister)
        etConfirm=findViewById(R.id.etConfirm)
        btnRegister.setOnClickListener {

            var fname=etFname.text.toString()
            var lname=etLname.text.toString()
            var age:Int=etAge.text.toString().toInt()
            var address=etAddress.text.toString()
            var email=etEmail.text.toString()
            var gender=""
            if(rdbMale.isChecked){
                gender="Male"
            }
            else if(rdbFemale.isChecked){
                gender="Female"
            }
            else{
                gender="Others"
            }
            var password=etPassword.text.toString()
            var confirmpassword=etConfirm.text.toString()
            if(password!=confirmpassword){
                etPassword.error="Password doesn't match"
                etPassword.requestFocus()
                return@setOnClickListener
            }
            else{
                val patient= Patient(fname,lname,age,gender,address,email,password)
                CoroutineScope(Dispatchers.IO).launch {
                    database
                        .getInstance(this@PatientRegistrationActivity)
                        .getUserDAO()
                        .registerUser(patient)
                    withContext(Dispatchers.Main){
                        startActivity(Intent(this@PatientRegistrationActivity,LoginActivity::class.java))
                        Toast.makeText(this@PatientRegistrationActivity, "Patient registered", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}