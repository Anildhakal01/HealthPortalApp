package com.anil25a.healthportal.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anil25a.healthportal.dao.HospitalDAO
import com.anil25a.healthportal.dao.UserDAO
import com.anil25a.healthportal.entity.Hospital
import com.anil25a.healthportal.entity.Patient


@Database(
    entities = [(Patient::class),(Hospital::class)],
    version = 1
)
abstract class database: RoomDatabase() {
    abstract fun getUserDAO(): UserDAO
    abstract fun getHospitalDAO(): HospitalDAO
    companion object {
        @Volatile
        private var instance: database? = null
        fun getInstance(context: Context): database {
            if (instance == null) {
                synchronized(database::class) {
                    instance=buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                database::class.java,
                "HealthPortalDB"
            ).build()

    }
}