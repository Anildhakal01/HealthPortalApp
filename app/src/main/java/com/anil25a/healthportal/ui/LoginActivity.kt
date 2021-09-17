package com.anil25a.healthportal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.anil25a.healthportal.R
import com.anil25a.healthportal.entity.Hospital
import com.anil25a.healthportal.repository.HospitalRepository
import com.anil25a.healthportal.repository.PatientRepository
import com.anil25a.healthportal.ui.admin.AdminDashboardActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var tvlRegister: TextView
    private lateinit var btnLogin: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var constLayout: LinearLayout
    var _id=""
    var name=""
    var type=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        tvlRegister=findViewById(R.id.tvlSignup)
        btnLogin=findViewById(R.id.btnLogin)
        constLayout=findViewById(R.id.constlayout)
        getSharePref()

        val userTypes = resources.getStringArray(R.array.userTypes)
        var usertype=""
        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, userTypes
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {

                     usertype=userTypes[position];
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
            tvlRegister.setOnClickListener {
                if(usertype=="Hospital")
                {
                    startActivity(Intent(this@LoginActivity, HospitalRegistrationActivity::class.java))
                }
                else
                {
                    startActivity(Intent(this@LoginActivity, PatientRegistrationActivity::class.java))
                }

            }


            btnLogin.setOnClickListener {
                var email = etEmail.text.toString()
                var password = etPassword.text.toString()
                if(usertype=="Admin"){

                    if(email.equals("admin@gmail.com")&&password.equals("admin@123"))
                    {
                        startActivity(Intent(this@LoginActivity, AdminDashboardActivity::class.java))
                    }
                    else{
                        Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    }
                }
                else if(usertype=="Hospital"){
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
//                        val db = database.getInstance(this@LoginActivity)
                            val repository = HospitalRepository()
                            val response = repository.logHospital(email, password)
                            val data= response.data
                            _id=data!!._id
                            type="Hospital"
                            name=data!!.name.toString()
                            if (response.success == true) {
                                saveSharePref()
                                val intent =
                                    Intent(applicationContext, HomeActivity::class.java).apply {
                                        putExtra("Email", data!!.name)
                                        putExtra("_id",data!!._id)
                                        putExtra("usertype","Hospital")
                                    }
                                startActivity(intent)
                                finish()
                            } else {
                                withContext(Dispatchers.Main) {
                                    val snack =
                                        Snackbar.make(
                                            constLayout,
                                            "Invalid credentials",
                                            Snackbar.LENGTH_LONG
                                        )
                                    snack.setAction("OK", View.OnClickListener {
                                        snack.dismiss()
                                    })
                                    snack.show()
                                }
                            }

                        } catch (ex: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Invalid Credentials", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
//                }
                    }
                }
                else{
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = PatientRepository()
                    val response = repository.checkPatient(email, password)
                    val data= response.data
                    type="Patient"
                     _id=data!!._id
                    name= data!!.fname.toString()
                    if (response.success == true) {
                        saveSharePref()
                        val intent =
                            Intent(applicationContext, HomeActivity::class.java).apply {
                                putExtra("Email", data!!.fname)
                                putExtra("_id",data!!._id)
                                putExtra("usertype","Patient")

                            }
                        startActivity(intent)
                        finish()
                    } else {
                        withContext(Dispatchers.Main) {
                            val snack =
                                Snackbar.make(
                                    constLayout,
                                    "Invalid credentials",
                                    Snackbar.LENGTH_LONG
                                )
                            snack.setAction("OK", View.OnClickListener {
                                snack.dismiss()
                            })
                            snack.show()
                        }
                    }

                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid Credentials", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
//                }
            }
        }
    }

            }

    }
    private fun saveSharePref() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        val sharedPref = getSharedPreferences("LogData", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("_id",_id)
        editor.putString("name",name)
        editor.putString("type",type)
        editor.apply()
    }
    private fun getSharePref()
    {
        val sharedPref = getSharedPreferences("LogData", MODE_PRIVATE)
        val email = sharedPref.getString("email", "")
        if(email!=null&&!email.equals("")&&!_id.equals(null)){
            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
        }
    }
}