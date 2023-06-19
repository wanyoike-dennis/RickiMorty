package com.example.rickimorty.ui.homepage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rickimorty.data.models.CharacterDomain
import com.example.rickimorty.local.dao.CharacterDao
import com.example.rickimorty.data.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _characters = MutableLiveData<List<CharacterDomain>>()
    val characters:LiveData<List<CharacterDomain>> = _characters

    val isLoading = MutableLiveData<Boolean>()

    companion object{
        private const val TAG = "MovieViewModel"
    }

    private fun getAllCharacters(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val charactersFromDb = repository.getMoviesFromDb()
                try {
                    isLoading.value= true
                    if (charactersFromDb.isEmpty()) {
                        val charactersFromApi = repository.getDataFromApi()
                        repository.cacheCharacters(charactersFromApi)
                        _characters.postValue(charactersFromApi)
                        isLoading.value=false
                    } else {
                        isLoading.value = true
                        _characters.postValue(charactersFromDb)
                        isLoading.value = false

                    }

                } catch (e: Exception) {
                    Log.e(TAG, "failed to fetch characters", e)

                }
                try {
                    val data = repository.getDataFromApi()
                    _characters.postValue(data)
                } catch (e: Exception) {
                    Log.e("view model", "could not load data", e)
                }
            }
        }
    }
    init {
        getAllCharacters()
    }
}



@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(private val repository: CharacterRepository, private val dao: CharacterDao) :
        ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)){
            return MyViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }
}