package com.example.rickimorty.data.mappers

import com.example.rickimorty.data.models.CharacterDomain
import com.example.rickimorty.local.model.CharacterEntity
import com.example.rickimorty.remote.CharacterDto


fun CharacterEntity.toDomain(): CharacterDomain {
    return CharacterDomain(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        origin = origin.toDomain(),
        location = location.toDomain(),
        image = image
    )
}

fun CharacterDto.toDomain(): CharacterDomain {
    return CharacterDomain(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        origin = origin.toDomain(),
        location = location.toDomain(),
        image = image
    )
}



fun CharacterDomain.toCharactersEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        origin = origin.toEntity(),
        location = location.toEntity(),
        image = image
    )
}


fun CharacterDomain.toCharactersDto(): CharacterDto {
    return CharacterDto(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        origin = origin.toDto(),
        location = location.toDto(),
        image = image
    )
}
