package com.example.rickimorty

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rickimorty.local.dao.CharacterDao
import com.example.rickimorty.local.database.CharacterDatabase
import com.example.rickimorty.local.model.CharacterEntity
import com.google.common.truth.Truth.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class CharacterEntityReadWriteTest {
    private lateinit var dao: CharacterDao
    private lateinit var db: CharacterDatabase

    companion object{
        private fun getEntity(id:Int,name:String) = CharacterEntity(
            id=id,
            name = name,
            status = "Alive",
            species = "Alien",
            gender = "Male",
            origin = CharacterEntity.Origin("SpaceJam"),
            location= CharacterEntity.Location("Neo"),
            image = ""
        )
    }

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CharacterDatabase::class.java
        ).build()
        dao = db.characterDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun given_characterDao_when_given_characters_should_return_an_empty_list()=
        runBlocking {
            val characters = dao.getCharacters()
            assertThat(characters).isEmpty()
        }

    @Test
    @Throws(Exception::class)
    fun given_dao_when_saving_a_character_should_save_Character_sucesful()=
        runBlocking {
            val character = getEntity(1,"Dennis")
            dao.insertOne(character)
            val characters = dao.getCharacters()
            val first: CharacterEntity = characters.first()
            assertThat(character).isEqualTo(first)
        }

    @Test
    @Throws(IOException::class)
    fun given_dao_when_deleting_a_character_should_delete_Character_sucesful() {
        runBlocking {
            val character = getEntity(1, "dennis")
            dao.insertOne(character)
            dao.deleteCharacter(character)
            val characters = dao.getCharacters()
            assertThat(characters.isEmpty())
        }
    }
    @Test
    @Throws(Exception::class)
    fun searching_a_character_return_the_correct_character()=
        runBlocking {
            val first = getEntity(1,"dennis")
            val second = getEntity(2,"alice")
            dao.insertOne(first)
            dao.insertOne(second)
            val characters = dao.searchCharacters("dennis")
            assertThat(characters).contains(first)
        }


}