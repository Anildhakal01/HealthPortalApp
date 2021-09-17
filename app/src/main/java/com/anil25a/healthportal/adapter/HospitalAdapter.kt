package com.anil25a.healthportal.adapter
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anil25a.healthportal.R
import com.anil25a.healthportal.api.ServiceBuilder
import com.anil25a.healthportal.entity.Hospital
import com.anil25a.healthportal.ui.AppointmentRequestActivity
import com.bumptech.glide.Glide
import android.app.Activity
import com.anil25a.healthportal.ui.DashboardActivity


class HospitalAdapter (
        val context: Context,
        val lstHospital:MutableList<Hospital>,
        val patientId:String

    ):
            RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {
        class HospitalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ivHospital: ImageView
            val homelayout:CardView
            val tvName: TextView
            val tvLocation:TextView
            val tvDesc: TextView
            init {
                homelayout=view.findViewById(R.id.homelayout)
                ivHospital=view.findViewById(R.id.ivHospital)
                tvName = view.findViewById(R.id.tvName)
                tvLocation = view.findViewById(R.id.tvLocation)
                tvDesc = view.findViewById(R.id.tvDesc)

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_hospital, parent, false)

            return HospitalViewHolder(view)
        }

        override fun getItemCount(): Int {
            return lstHospital.size
        }

        override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
            val hospital = lstHospital[position]
            holder.tvName.text = hospital.name.toString()
            holder.tvLocation.text=hospital.location.toString()
            holder.tvDesc.text = hospital.desc.toString()
            Glide.with(context).load(hospital.himage).into(holder.ivHospital)
            val imagePath = ServiceBuilder.loadImagePath() + hospital.himage
            if (!hospital.himage.equals(null)) {
                Glide.with(context)
                        .load(imagePath)
                        .fitCenter()
                        .into(holder.ivHospital)
            }
            holder.homelayout.setOnClickListener {

                val intent = Intent(context, AppointmentRequestActivity::class.java)
//                val emailIntent = Intent(context,DashboardActivity::class.java)
//                val email = emailIntent.getStringExtra("email")
                intent.putExtra("imagepath",imagePath)
                intent.putExtra("name",hospital.name)
                intent.putExtra("location",hospital.location)
                intent.putExtra("desc",hospital.desc)
                intent.putExtra("email",hospital.email)
                intent.putExtra("hospitalId",hospital._id)
                intent.putExtra("patientId",patientId)
                context.startActivity(intent)
            }
        }
    }
