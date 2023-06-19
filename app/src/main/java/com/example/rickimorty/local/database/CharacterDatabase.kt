package com.example.rickimorty.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickimorty.local.model.CharacterEntity
import com.example.rickimorty.local.model.LocationToStringConverter
import com.example.rickimorty.local.model.OriginToStringConverter
import com.example.rickimorty.local.dao.CharacterDao

@TypeConverters(OriginToStringConverter::class, LocationToStringConverter::class)
@Database(entities = [CharacterEntity::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object{

        @Volatile
        private  var INSTANCE: CharacterDatabase? = null
        fun getDatabase(context: Context) : CharacterDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDatabase::class.java,
                    "Rick&Morty"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}