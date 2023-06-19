package com.example.rickimorty.data.models



data class LocationDomain(
    val name:String
)
data class OriginDomain(
    val name:String
)

data class CharacterDomain(
    val id: Int,
    val name:String,
    val status:String,
    val species:String,
    val gender:String,
    val origin: OriginDomain,
    val location: LocationDomain,
    val image:String,
)



