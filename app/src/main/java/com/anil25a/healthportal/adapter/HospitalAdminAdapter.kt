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
import com.anil25a.healthportal.api.ServiceBuilder
import com.anil25a.healthportal.db.HealthPortalDatabase
import com.anil25a.healthportal.entity.Hospital
import com.anil25a.healthportal.repository.HospitalRepository
import com.anil25a.healthportal.ui.HospitalRegistrationActivity
import com.anil25a.healthportal.ui.admin.AdminDashboardActivity
import com.anil25a.healthportal.ui.admin.HospitalAdminActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HospitalAdminAdapter(val context: Context,
                           val lstHospital: MutableList<Hospital>
):
    RecyclerView.Adapter<HospitalAdminAdapter.HospitalAdminViewHolder>() {
    class HospitalAdminViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgHospital:ImageView
        val tvName: TextView
        val tvDesc: TextView
        val tvAddress: TextView
        val ivEdit:ImageView
        val ivDelete:ImageView
        init {
            tvName = view.findViewById(R.id.tvName)
            tvDesc = view.findViewById(R.id.tvDesc)
            tvAddress = view.findViewById(R.id.tvAddress)
            imgHospital=view.findViewById(R.id.imgHospital)
            ivEdit=view.findViewById(R.id.ivEdit)
            ivDelete=view.findViewById(R.id.ivDelete)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalAdminViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_hospitaladmin, parent, false)

        return HospitalAdminViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstHospital.size
    }

    override fun onBindViewHolder(holder: HospitalAdminViewHolder, position: Int) {
        val hospital = lstHospital[position]

        holder.tvName.text =hospital.name.toString()
        holder.tvAddress.text = hospital.location.toString()
        holder.tvDesc.text = hospital.desc.toString()
        Glide.with(context).load(hospital.himage).into(holder.imgHospital)
        val imagePath = ServiceBuilder.loadImagePath() + hospital.himage
        if (!hospital.himage.equals(null)) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.imgHospital)
        }
        holder.ivEdit.setOnClickListener {
            val intent = Intent(context, HospitalRegistrationActivity::class.java)
            intent.putExtra("_id",hospital._id)
            intent.putExtra("imagepath",imagePath)
            intent.putExtra("name",hospital.name)
            intent.putExtra("location",hospital.location)
            intent.putExtra("email",hospital.email)
            intent.putExtra("desc",hospital.desc)
            intent.putExtra("password",hospital.password)
            context.startActivity(intent)
        }
        holder.ivDelete.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete this property")
            builder.setMessage("Are you sure you want to delete ${hospital.name} ??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val hospitalrepo = HospitalRepository()
                        val response = hospitalrepo.deleteHospital(hospital._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Hospital Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(context, HospitalAdminActivity::class.java)
                                context.startActivity(intent)

                            }
                            withContext(Dispatchers.Main) {
                                lstHospital.remove(hospital)
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