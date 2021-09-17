package com.anil25a.healthportal.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anil25a.healthportal.dao.HospitalDAO
import com.anil25a.healthportal.dao.PatientDAO
import com.anil25a.healthportal.entity.Hospital
import com.anil25a.healthportal.entity.Patient


@Database(
    entities = [(Patient::class),(Hospital::class)],
    version = 1
)
abstract class HealthPortalDatabase: RoomDatabase() {
    abstract fun getUserDAO(): PatientDAO
    abstract fun getHospitalDAO(): HospitalDAO
    companion object {
        @Volatile
        private var instance: HealthPortalDatabase? = null
        fun getInstance(context: Context): HealthPortalDatabase {
            if (instance == null) {
                synchronized(HealthPortalDatabase::class) {
                    instance=buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                HealthPortalDatabase::class.java,
                "HealthPortalDB"
            ).build()

    }
}