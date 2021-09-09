package com.anil25a.healthportal.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.*
import androidx.core.app.ActivityCompat
import com.anil25a.healthportal.R
import com.anil25a.healthportal.repository.HospitalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class HospitalRegistrationActivity : AppCompatActivity() {
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    private lateinit var btnRegister: Button
    private lateinit var etHname: EditText
    private lateinit var etDesc: EditText
    private lateinit var etLocation: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirm: EditText
    private lateinit var ivClick: ImageView
    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_registration)


        btnRegister = findViewById(R.id.btnRegister)
        etHname = findViewById(R.id.etHname)
        etDesc = findViewById(R.id.etDesc)
        etLocation = findViewById(R.id.etLocation)
        etEmail = findViewById(R.id.etBemail)
        etPassword = findViewById(R.id.etPassword)
        etConfirm = findViewById(R.id.etConfirm)
        ivClick=findViewById(R.id.ivClick)
        checkRunTimePermission()
        btnRegister.setOnClickListener {
            addHospital()
        }
        ivClick.setOnClickListener{
            loadPopUpMenu()
        }
    }
    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@HospitalRegistrationActivity, permissions, 1)
    }


    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(this@HospitalRegistrationActivity, ivClick)
        popupMenu.menuInflater.inflate(R.menu.cameragallery, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                ivClick.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                ivClick.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
    private fun addHospital() {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            var hname = etHname.text.toString()
            var desc = etDesc.text.toString()
            var location = etLocation.text.toString()
            var email = etEmail.text.toString()
            var password = etPassword.text.toString()
            var confirmpassword = etConfirm.text.toString()
            if (password != confirmpassword) {
                etPassword.error = "Password doesn't match"
                etPassword.requestFocus()
            } else {
                val rHname: RequestBody =
                    RequestBody.create(MediaType.parse("text/plain"), hname)
                val rDesc: RequestBody =RequestBody.create(MediaType.parse("text/plain"), desc)
                val rPassword: RequestBody =RequestBody.create(MediaType.parse("text/plain"), password)
                val rLocation: RequestBody =RequestBody.create(MediaType.parse("text/plain"), location)
                val rEmail: RequestBody =RequestBody.create(MediaType.parse("text/plain"), email)
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = HospitalRepository()
                    val response = repository.registerHospital(
                        hname = rHname,
                        desc = rDesc,
                        location = rLocation,
                        imgbody = MultipartBody.Part.createFormData("himage", file.name, reqFile),
                        email = rEmail,
                        password = rPassword
                    )
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@HospitalRegistrationActivity,
                                "Success",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            finish()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@HospitalRegistrationActivity,
                                "Registration error",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                }

            }

        }
    }
}