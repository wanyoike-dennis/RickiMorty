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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private val _page = MutableLiveData(1)
    val page:LiveData<Int> = _page


    companion object{
        private const val TAG = "MovieViewModel"
    }

     private fun getAllCharacters(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.postValue(true)
                val charactersFromDb = repository.cachedData(_page.value!!)
                repository.cacheCharacters(charactersFromDb)
                _characters.postValue(charactersFromDb)
                _isLoading.postValue(false)
            }
        }
    }
    init {
        getAllCharacters()
    }


    fun loadNextCharacters(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                _isLoading.postValue(true)
                _page.postValue(_page.value!!.inc())
                val newList= repository.getDataFromApi(_page.value!!)
                repository.cacheCharacters(newList)
                _characters.postValue(newList)
                _isLoading.postValue(false)
                Log.e(TAG,"loading next page")
            }
        }
    }

    fun searchCharacter(query:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _isLoading.postValue(true)
                val results = repository.searchCharacters(query)
                _characters.postValue(results)
                _isLoading.postValue(false)

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