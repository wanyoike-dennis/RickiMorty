package com.example.rickimorty.local.model

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OriginToStringConverter {

    @TypeConverter
    fun fromOriginToString(Origin: CharacterEntity.Origin):String =
        Json.encodeToString(Origin)

    @TypeConverter
    fun fromStringToOrigin(string: String): CharacterEntity.Origin =
        Json.decodeFromString(string)
}