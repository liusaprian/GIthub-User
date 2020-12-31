package com.example.githubuser.viewmodel

import androidx.lifecycle.*
import com.example.githubuser.repository.UserRepository
import com.example.githubuser.entity.User
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FavoriteUserViewModel(private val repository: UserRepository) : ViewModel() {

    fun getFavoriteUser(username: String) = repository.getFavoriteUser(username).asLiveData()

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun delete(username: String) = viewModelScope.launch {
        repository.delete(username)
    }
}

class FavoriteUserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}