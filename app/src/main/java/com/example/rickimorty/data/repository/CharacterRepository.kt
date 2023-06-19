package com.example.rickimorty.data.repository

import com.example.rickimorty.data.models.CharacterDomain
import com.example.rickimorty.data.mappers.toCharactersEntity
import com.example.rickimorty.data.mappers.toDomain
import com.example.rickimorty.local.dao.CharacterDao
import com.example.rickimorty.remote.MortyApi
import retrofit2.HttpException

class CharacterRepository(private val apiService: MortyApi, private val dao: CharacterDao) {

    suspend fun getDataFromApi() : List<CharacterDomain>{
        val response = apiService.retrofitService.getCharacter()
        if (response.isSuccessful){
            return response.body()?.results ?: emptyList()
        }
        else {
            throw HttpException(response)
        }
    }

    fun getMoviesFromDb():List<CharacterDomain>{
          return  dao.getCharacters().map { it.toDomain() }
    }

    suspend fun cacheCharacters(characters:List<CharacterDomain>){
        val characterEntity = characters.map { it.toCharactersEntity() }
        dao.insert(characterEntity)
    }
}