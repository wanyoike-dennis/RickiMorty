package com.example.rickimorty.remote

import com.example.rickimorty.data.models.CharacterDomain

data class ApiResponse(
    val info:Info,
    val results:List<CharacterDomain>
)

data class Info(
    val next : String,
    val prev: String
)
