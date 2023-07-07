package com.example.rickimorty.remote

import com.example.rickimorty.data.models.CharacterDomain

data class ApiResponse(
    val results:List<CharacterDomain>
)
