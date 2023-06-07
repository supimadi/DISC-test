package org.d3if3038.assesment1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PersonalityEntity::class], version = 3, exportSchema = false)
abstract class PersonalityDb : RoomDatabase() {

    abstract val dao: PersonalityDao

    companion object {
        @Volatile
        private var INSTANCE: PersonalityDb? = null

        fun getInstance(context: Context) : PersonalityDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PersonalityDb::class.java,
                        "personality.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}