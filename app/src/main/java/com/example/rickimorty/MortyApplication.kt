package com.example.rickimorty

import android.app.Application
import com.example.rickimorty.local.database.CharacterDatabase

class MortyApplication : Application() {

    val database : CharacterDatabase by lazy {
        CharacterDatabase.getDatabase(this)
    }
}