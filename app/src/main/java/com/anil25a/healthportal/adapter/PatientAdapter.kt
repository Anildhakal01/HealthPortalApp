package com.anil25a.healthportal.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.anil25a.healthportal.R
import com.anil25a.healthportal.entity.Patient
import com.anil25a.healthportal.repository.PatientRepository
import com.anil25a.healthportal.ui.PatientRegistrationActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PatientAdapter(val context: Context,
                       val lstPatient: MutableList<Patient>
):
    RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {
    class PatientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView
        val tvLname:TextView
        val tvAge: TextView
        val tvGender: TextView
        val tvAddress: TextView
        val tvEmail: TextView
        val ivEdit: ImageView
        val ivDelete: ImageView

        init {
            tvName = view.findViewById(R.id.tvName)
            tvLname=view.findViewById(R.id.tvLname)
            tvAge = view.findViewById(R.id.tvAge)
            tvAddress = view.findViewById(R.id.tvAddress)
            tvGender=view.findViewById(R.id.tvGender)

            tvEmail=view.findViewById(R.id.tvEmail)
            ivEdit=view.findViewById(R.id.ivEdit)
            ivDelete=view.findViewById(R.id.ivDelete)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_patientadmin, parent, false)

        return PatientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstPatient.size
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = lstPatient[position]
        holder.tvName.text = patient.fname.toString()
        holder.tvLname.text = patient.lname.toString()
        holder.tvAge.text = patient.age.toString()
        holder.tvGender.text = patient.gender.toString()
        holder.tvEmail.text = patient.email.toString()
        holder.tvAddress.text = patient.address.toString()

        holder.ivEdit.setOnClickListener {
            val intent = Intent(context, PatientRegistrationActivity::class.java)
            intent.putExtra("id",patient._id)
            intent.putExtra("fname",patient.fname)
            intent.putExtra("lname",patient.lname)
            intent.putExtra("age",patient.age)
            intent.putExtra("address",patient.address)
            intent.putExtra("gender",patient.gender)
            intent.putExtra("email",patient.email)
            context.startActivity(intent)
        }
        holder.ivDelete.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete this property")
            builder.setMessage("Are you sure you want to delete ${patient.fname} ??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val userrepo = PatientRepository()
                        val response = userrepo.deletePatientById(patient._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
//                                HealthPortalDatabase.getInstance(context).getUserDAO()
//                                    .deleteUserbyId(patient)
                                Toast.makeText(
                                    context,
                                    "User Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            withContext(Dispatchers.Main) {
                                lstPatient.remove(patient)
                                notifyDataSetChanged()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                ex.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }
}