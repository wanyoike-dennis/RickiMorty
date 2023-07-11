package com.example.rickimorty.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickimorty.local.model.CharacterEntity


@Dao
interface CharacterDao {

    @Query("SELECT * FROM CharactersTable")
    fun getCharacters() : List<CharacterEntity>

    @Query("SELECT * FROM CharactersTable where name LIKE '%' || :query || '%' ")
    suspend fun searchCharacters(query:String) : List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOne(character:CharacterEntity)

    @Delete
    suspend fun deleteCharacter(character:CharacterEntity)

    @Query("DELETE FROM CharactersTable")
    suspend fun deleteAllCharacters()
}