package com.example.threadclone.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MemberStatusEntity::class], version = 5, exportSchema = false)
abstract class Databse : RoomDatabase() {

    abstract fun getDao() : Dao

    companion object{
        @Volatile
        var INSANCE : Databse?=null

        fun getDatabaseInstance(context: Context): Databse {
            val tempInstance= INSANCE
            if (tempInstance!=null) return tempInstance
            else{
                synchronized(this){
                    val roomdb= Room.databaseBuilder(context,Databse::class.java,"DEMO")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSANCE=roomdb
                    return roomdb
                }
            }
        }
    }
}