package com.example.rickimorty.ui.homepage

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rickimorty.data.models.CharacterDomain
import com.example.rickimorty.local.dao.CharacterDao
import com.example.rickimorty.data.repository.CharacterRepository
import com.example.rickimorty.utils.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _characters = MutableLiveData<List<CharacterDomain>>()
    val characters:LiveData<List<CharacterDomain>> = _characters

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    val page = MutableLiveData(0)




    companion object{
        private const val TAG = "MovieViewModel"
    }

     private fun getAllCharacters(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val charactersFromDb = repository.getMoviesFromDb()
                try {
                    _isLoading.postValue(true)
                    if (charactersFromDb.isEmpty()) {
                        val charactersFromApi = repository.getDataFromApi(page.value!!)
                        repository.cacheCharacters(charactersFromApi)
                        _characters.postValue(charactersFromApi)
                        _isLoading.postValue(false)
                    } else {
                        _isLoading.postValue(true)
                        _characters.postValue(charactersFromDb)
                        _isLoading.postValue(false)
                    }

                } catch (e: Exception) {
                    Log.e(TAG, "failed to fetch characters", e)
                }
            }
        }
    }
    init {
        getAllCharacters()
    }

    fun currentPage() : Int {
        return page.value!!
    }

    fun loadNextCharacters(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    _isLoading.postValue(true)
                    page.postValue(page.value!!.inc())
                    val list = repository.getDataFromApi(page.value!!)
                    repository.cacheCharacters(list)
                    _characters.postValue(list)
                    _isLoading.postValue(false)
                } catch (e:Exception){
                    Log.e(TAG,"failed to fetch characters",e)
                }


            }
        }
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