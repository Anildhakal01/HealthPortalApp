package com.anil25a.healthportal.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.anil25a.healthportal.R
import com.anil25a.healthportal.repository.HospitalRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var tvlRegister: TextView
    private lateinit var tvlRegisterP: TextView
    private lateinit var btnLogin: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var constLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        tvlRegister=findViewById(R.id.tvlSignupHospital)
        tvlRegisterP=findViewById(R.id.tvlSignupPatient)
        btnLogin=findViewById(R.id.btnLogin)
        constLayout=findViewById(R.id.constlayout)
        getSharePref()


        tvlRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, HospitalRegistrationActivity::class.java))
        }
        tvlRegisterP.setOnClickListener {
            startActivity(Intent(this@LoginActivity, PatientRegistrationActivity::class.java))
        }



        btnLogin.setOnClickListener {
            var email = etEmail.text.toString()
            var password = etPassword.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                try {
//                        val db = database.getInstance(this@LoginActivity)
                    val repository = HospitalRepository()
                    val response = repository.logHospital(email, password)
                    if (response.success == true) {
                        saveSharePref()
//                        ServiceBuilder.token = "Bearer " + response.token
                        val intent =
                            Intent(applicationContext, DashboardActivity::class.java).apply {
                                putExtra("Email", email)
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
    private fun saveSharePref() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val sharedPref = getSharedPreferences("LogData", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }
    private fun getSharePref()
    {
        val sharedPref = getSharedPreferences("LogData", MODE_PRIVATE)
        val email = sharedPref.getString("email", "")
        if(email!=null&&!email.equals("")){
            startActivity(Intent(this@LoginActivity,DashboardActivity::class.java))
        }
    }
}