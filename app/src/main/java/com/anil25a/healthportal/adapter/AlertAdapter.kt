package com.anil25a.healthportal.adapter;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.anil25a.healthportal.R
import com.anil25a.healthportal.entity.Appointment


class AlertAdapter (
    val context: Context,
    val lstAppointment:MutableList<Appointment>

):
    RecyclerView.Adapter<AlertAdapter.AlertViewHolder>() {
    class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTime: TextView
        val tvlTime: TextView
        val tvAlerts:TextView
        init {
            tvTime = view.findViewById(R.id.tvTime)
            tvAlerts = view.findViewById(R.id.tvAlerts)
            tvlTime = view.findViewById(R.id.tvlTime)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_appoint_alert, parent, false)
        return AlertViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstAppointment.size
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val appointment = lstAppointment[position]
        val doctorName = appointment.doctorName
        val requestStatus = appointment.requestStatus
        if (requestStatus == "Accepted") {
            holder.tvAlerts.text = "Your Appointment with $doctorName has been confirmed!!!"
            holder.tvTime.text = appointment.dateTime.toString()
        }
        else if (requestStatus == "Rejected") {
            holder.tvAlerts.text =
                "Apologies! Your request has been cancelled due to technical reasons.Please contact hospital staff!!!"
            holder.tvTime.isVisible = false
            holder.tvlTime.isVisible=false
        } else {
            holder.tvAlerts.text = "Your appointment request is being processed!!!"
//            holder.tvTime.isVisible = false
        }

    }
}

