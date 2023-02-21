package com.example.ads.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ads.model.User
import com.example.ads.repositories.AdRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.*
import com.example.ads.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel  @Inject constructor(val adRepo: AdRepo) :ViewModel(){

    private val _loginLiveData = MutableLiveData<String>()
    val loginLiveData: LiveData<String> get() = _loginLiveData

    private val _recyclerLiveDataError = MutableLiveData<String>()
    val recyclerLiveDataError: LiveData<String> get() = _recyclerLiveDataError

//    init {
//        login()
//    }

     fun login(user: User) = viewModelScope.launch(Dispatchers.IO){
        adRepo.loginUser(user.name,user.password).collectLatest {
            when(it.status){
                Status.LOADING->{}
                Status.SUCCESS->{
                    _loginLiveData.postValue(it.data!!)
                }
                Status.ERROR->{
                    _recyclerLiveDataError.postValue(it.message.toString())
                }
            }
        }
    }
}
