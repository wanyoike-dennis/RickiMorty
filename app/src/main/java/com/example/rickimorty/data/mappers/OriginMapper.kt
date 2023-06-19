package com.example.rickimorty.data.mappers

import com.example.rickimorty.data.models.OriginDomain
import com.example.rickimorty.local.model.CharacterEntity
import com.example.rickimorty.remote.CharacterDto

fun CharacterEntity.Origin.toDomain()= OriginDomain(
    name=name
)

fun CharacterDto.Origin.toDomain()= OriginDomain(name)
fun OriginDomain.toEntity() = CharacterEntity.Origin(name)
fun OriginDomain.toDto() = CharacterDto.Origin(name)