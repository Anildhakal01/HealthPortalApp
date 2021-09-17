package com.anil25a.healthportal.adapter;

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.anil25a.healthportal.R
import com.anil25a.healthportal.entity.Appointment
import com.anil25a.healthportal.repository.HospitalRepository
import com.anil25a.healthportal.repository.PatientRepository
import com.anil25a.healthportal.ui.AppointmentRequestActivity
import com.anil25a.healthportal.ui.VerifyAppointmentActivity
import com.anil25a.healthportal.ui.admin.PatientAdminActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


public class AppointmentAdapter (
        val context: Context,
        val lstAppointment:MutableList<Appointment>,
        val hospitalId:String

):
        RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {
class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIssue:TextView
        val tvName:TextView
        val tvAppointmentTime: TextView
        val tvAge:TextView
        val tvGender: TextView
        val tvDoctor:TextView
        val btnAccept: Button
        val btnReject:Button
        init {
        tvName=view.findViewById(R.id.tvName)
        tvIssue = view.findViewById(R.id.tvIssue)

        tvAppointmentTime = view.findViewById(R.id.tvAppointmentTime)
        tvAge = view.findViewById(R.id.tvAge)
                tvGender = view.findViewById(R.id.tvGender)
                tvDoctor = view.findViewById(R.id.tvDoctor)
                btnAccept=view.findViewById(R.id.btnAccept)
                btnReject=view.findViewById(R.id.btnReject)

        }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.layout_appointment, parent, false)
        return AppointmentViewHolder(view)
        }

        override fun getItemCount(): Int {
        return lstAppointment.size
        }

        override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = lstAppointment[position]
//                val patientId= appointment.patientId?._id.toString()
                val requestStatus=appointment.requestStatus
                if(requestStatus=="Accepted"){
                        holder.btnAccept.text="Accepted"
                        holder.btnReject.isVisible=false
                }
                if(requestStatus=="Rejected")
                {
                        holder.btnReject.text="Rejected"
                        holder.btnAccept.isVisible=false
                }
                holder.tvIssue.text = appointment.issue.toString()
        holder.tvAppointmentTime.text=appointment.dateTime.toString()
//        holder.tvName.text = appointment.patientId?.fname.toString()
                holder.tvDoctor.text = appointment.doctorName.toString()
//                holder.tvAge.text = appointment.patientId?.age.toString()
//                holder.tvGender.text = appointment.patientId?.gender.toString()
        holder.btnAccept.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                        val repository = HospitalRepository()
                        val response = repository.verifyAppointment(patientId="patientId",
                        hospitalId=hospitalId)
                        if (response.success == true) {
                                withContext(Dispatchers.Main) {
//
                                        Toast.makeText(
                                                context,
                                                "Appointment Accepted",
                                                Toast.LENGTH_SHORT
                                        )
                                                .show()

//                                        val intent = Intent(context, VerifyAppointmentActivity::class.java)
//                                        context.startActivity(intent)
                                }
                        } else {
                                withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                                context,
                                                "Error",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                }
                        }
                }
        }
                holder.btnReject.setOnClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                                val repository = HospitalRepository()
                                val response = repository.rejectAppointment(patientId="patientId",
                                        hospitalId=hospitalId)
                                if (response.success == true) {
                                        withContext(Dispatchers.Main) {
                                                Toast.makeText(
                                                        context,
                                                        "Appointment Rejected",
                                                        Toast.LENGTH_SHORT
                                                )
                                                        .show()
                                                val intent = Intent(context, VerifyAppointmentActivity::class.java)
                                                context.startActivity(intent)
                                        }
                                } else {
                                        withContext(Dispatchers.Main) {
                                                Toast.makeText(
                                                        context,
                                                        "Error",
                                                        Toast.LENGTH_SHORT
                                                ).show()
                                        }
                                }
                        }
                }
        }
        }

