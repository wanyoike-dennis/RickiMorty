package com.example.rickimorty.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "CharactersTable")
data class CharacterEntity(
  @PrimaryKey(autoGenerate = false) val id : Int,
  @ColumnInfo(name = "name") val name:String,
  @ColumnInfo(name = "status") val status:String,
  @ColumnInfo(name = "species") val species:String,
  @ColumnInfo(name = "gender") val gender:String,
  @ColumnInfo(name = "origin") val origin: Origin,
  @ColumnInfo(name = "location") val location: Location,
  @ColumnInfo(name = "image") val image:String,
) {

  @Serializable
  data class Origin(
    val name: String,
  )

  @Serializable
  data class Location(
    val name: String
  )
}


