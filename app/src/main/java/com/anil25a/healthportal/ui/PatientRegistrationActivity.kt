package com.anil25a.healthportal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.isVisible
import com.anil25a.healthportal.R
import com.anil25a.healthportal.db.HealthPortalDatabase
import com.anil25a.healthportal.entity.Patient
import com.anil25a.healthportal.repository.PatientRepository
import com.anil25a.healthportal.ui.admin.PatientAdminActivity
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
        etFname = findViewById(R.id.etFname)
        etLname = findViewById(R.id.etLname)
        etAge = findViewById(R.id.etAge)
        etAddress = findViewById(R.id.etAddress)
        rdbMale = findViewById(R.id.rdbMale)
        rdbFemale = findViewById(R.id.rdbFemale)
        rdbOthers = findViewById(R.id.rdbOthers)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        etConfirm = findViewById(R.id.etConfirm)
            if (intent.extras != null) {
                etPassword.isVisible=false
                etConfirm.isVisible=false
                etFname.setText(intent.getStringExtra("fname"))
                etLname.setText(intent.getStringExtra("lname"))
                val age=intent.getIntExtra("age",0)
                etAge.setText(age.toString())
                etEmail.setText(intent.getStringExtra("email"))
                etAddress.setText(intent.getStringExtra("address"))
                val gender = intent.getStringExtra("gender")
                if (gender == "Male") {
                    rdbMale.isChecked = true
                } else if (gender == "Female") {
                    rdbFemale.isChecked = true
                } else {
                    rdbOthers.isChecked = true
                }
                btnRegister.setText("Update")
            }
            btnRegister.setOnClickListener {
                if (btnRegister.text == "Register") {
                    registerPatient()
                } else {
                    updatePatient()
                }

            }
    }

    private fun updatePatient() {
        var fname = etFname.text.toString()
        var lname = etLname.text.toString()
        var age = etAge.text.toString().toInt()
        var email = etEmail.text.toString()
        var address = etAddress.text.toString()
        val pid= intent.getStringExtra("_id")
        var gender = ""
        if (rdbMale.isChecked) {
            gender = "Male"
        } else if (rdbFemale.isChecked) {
            gender = "Female"
        } else {
            gender = "Others"
        }

        CoroutineScope(Dispatchers.IO).launch {
            val repository = PatientRepository()
            val response = repository.updatePatient(_id=pid!!,
                fname = fname,
                lname = lname,
                age = age,
                gender = gender,
                email = email,
                address = address,)
            if (response.success == true) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@PatientRegistrationActivity,
                        "Successfully Updated",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    startActivity(
                        Intent(
                            this@PatientRegistrationActivity,
                            PatientAdminActivity::class.java
                        )
                    )
                    finish()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@PatientRegistrationActivity,
                        "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun registerPatient() {
        var fname = etFname.text.toString()
        var lname = etLname.text.toString()
        var age = etAge.text.toString().toInt()
        var address = etAddress.text.toString()
        var gender = ""
        if (rdbMale.isChecked) {
            gender = "Male"
        } else if (rdbFemale.isChecked) {
            gender = "Female"
        } else {
            gender = "Others"
        }
        var email = etEmail.text.toString()
        var password = etPassword.text.toString()
        var confirmpassword = etConfirm.text.toString()
        if (password != confirmpassword) {
            etPassword.error = "Password doesn't match"
            etPassword.requestFocus()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = PatientRepository()
                val response = repository.registerPatient(
                    fname = fname,
                    lname = lname,
                    gender = gender,
                    age = age,
                    address = address,
                    email = email,
                    password = password
                )
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@PatientRegistrationActivity,
                            "Successfully Registered",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    finish()
                } else {

                    Toast.makeText(
                        this@PatientRegistrationActivity,
                        "Registration Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                }
            }
        }
    }