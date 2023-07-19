package com.example.day5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database( entities = [DetailEntity::class], version = 1)
abstract class DetailDB: RoomDatabase() {
    abstract fun getDao(): DetailDAO
    companion object{
        private var INSTANCE : DetailDB? = null

        fun getInstance(context: Context): DetailDB{
            synchronized(this){
                //var ins = INSTANCE
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,DetailDB::class.java,"demoDB.db").fallbackToDestructiveMigration().build()
                    //INSTANCE = ins
                }
                return INSTANCE as DetailDB
            }
        }
    }
}