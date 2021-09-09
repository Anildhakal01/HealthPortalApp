package com.anil25a.wearos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.wear.widget.BoxInsetLayout
import com.anil25a.wearos.repos.HospitalRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : Activity() {
    private lateinit var btnLogin: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var constLayout:BoxInsetLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        setContentView(R.layout.activity_login)
        btnLogin=findViewById(R.id.btnLogin)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login (){
        var email = etEmail.text.toString()
        var password = etPassword.text.toString()

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = HospitalRepository()
                val response = repository.logHospital(email, password)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val intent =
                        Intent(applicationContext, HomeActivity::class.java).apply {
                            putExtra("Email", email)
                        }
                        startActivity(intent)
                        finish()
                    }
                } else {
                    val snack =
                        Snackbar.make(
                            constLayout,
                            "Invalid",
                            Snackbar.LENGTH_LONG
                        )
                    snack.setAction("OK", View.OnClickListener {
                        snack.dismiss()
                    })
                    snack.show()
                }
            }
        }
        catch (ex:Exception){
            val snack =
                Snackbar.make(
                    constLayout,
                    "Invalid",
                    Snackbar.LENGTH_LONG
                )
            snack.setAction("OK", View.OnClickListener {
                snack.dismiss()
            })
            snack.show()
        }
    }
}