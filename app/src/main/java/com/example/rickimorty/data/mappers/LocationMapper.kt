package com.example.rickimorty.data.mappers

import com.example.rickimorty.data.models.LocationDomain
import com.example.rickimorty.local.model.CharacterEntity
import com.example.rickimorty.remote.CharacterDto

fun CharacterEntity.Location.toDomain()= LocationDomain(
    name=name
)

fun CharacterDto.Location.toDomain()= LocationDomain(name)
fun LocationDomain.toEntity() = CharacterEntity.Location(name)
fun LocationDomain.toDto() = CharacterDto.Location(name)