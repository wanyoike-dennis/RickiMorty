package com.example.rickimorty.remote

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto (
    val id: Int,
    val name:String,
    val status:String,
    val species:String,
    val gender:String,
    val origin: Origin,
    val location: Location,
    val image:String,
        ){
    @Serializable
    data class Origin(
        val name:String
    )

    @Serializable
    data class Location(
        val name:String
    )
}