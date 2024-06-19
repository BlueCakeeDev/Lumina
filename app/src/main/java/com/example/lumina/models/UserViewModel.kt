package com.example.lumina

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    fun setUserInfo(name: String?, email: String?) {
        _userName.value = name ?: "Usuario Anónimo"
        _userEmail.value = email ?: "usuario@example.com"
    }
}