package com.example.rickimorty.local.model

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LocationToStringConverter {

    @TypeConverter
    fun fromLocationToString(Location: CharacterEntity.Location):String =
        Json.encodeToString(Location)

    @TypeConverter
    fun fromStringToOrigin(string: String): CharacterEntity.Location =
        Json.decodeFromString(string)
}