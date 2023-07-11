package com.example.rickimorty.data.repository

import android.util.Log
import com.example.rickimorty.data.models.CharacterDomain
import com.example.rickimorty.data.mappers.toCharactersEntity
import com.example.rickimorty.data.mappers.toDomain
import com.example.rickimorty.local.dao.CharacterDao
import com.example.rickimorty.remote.MortyApi
import retrofit2.HttpException

class CharacterRepository(private val apiService: MortyApi, private val dao: CharacterDao) {

    suspend fun getDataFromApi(page:Int) : List<CharacterDomain>{

        val response = apiService.retrofitService.getCharacter(page)
        if (response.isSuccessful){
            return response.body()?.results ?: emptyList()
        }
        else {
            throw HttpException(response)
        }
    }

   suspend fun getDataFromDb(page:Int):List<CharacterDomain>{
      lateinit var characterList : List<CharacterDomain>
      try{
        characterList = dao.getCharacters().map { it.toDomain() }

      } catch (e:Exception){
          Log.e("Error" , e.message.toString())
      }
        if (characterList.isEmpty()){
            characterList = getDataFromApi(page)
            cacheCharacters(characterList)

        }
          return  characterList
    }

    suspend fun cachedData(page:Int) :List<CharacterDomain>{
        val characterList = ArrayList<CharacterDomain>()
         val list = getDataFromDb(page)
        characterList.clear()
        characterList.addAll(list)
        return characterList
    }

    suspend fun cacheCharacters(characters:List<CharacterDomain>){
        val characterEntity = characters.map { it.toCharactersEntity() }
        dao.insert(characterEntity)
    }

    suspend fun searchCharacters(query: String): List<CharacterDomain> {
        return dao.searchCharacters(query).map { it.toDomain() }
    }

    /*

    suspend fun updateCharactersList(page:Int) : List<CharacterDomain>{
        val newListOfCharacters = getDataFromApi(page)
        dao.deleteAllCharacters()
        dao.insert(newListOfCharacters)
        return newListOfCharacters.map { it.toDomain() }
    }

     */
}