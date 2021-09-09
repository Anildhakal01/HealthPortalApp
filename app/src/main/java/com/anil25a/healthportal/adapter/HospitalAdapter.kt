package com.anil25a.healthportal.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anil25a.healthportal.R
import com.anil25a.healthportal.api.ServiceBuilder
import com.anil25a.healthportal.entity.Hospital
import com.bumptech.glide.Glide

class HospitalAdapter (
        val context: Context,
        val lstHospital:MutableList<Hospital>

    ):
            RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {
        class HospitalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ivHospital: ImageView
            val tvName: TextView
            val tvLocation:TextView
            val tvDesc: TextView
            init {
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
        }
    }
