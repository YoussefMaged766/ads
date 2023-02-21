package com.example.ads.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ads.model.Ads
import com.example.ads.model.User
import com.example.ads.repositories.AdRepo
import com.example.ads.utils.Resource
import com.example.ads.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var adRepo: AdRepo):ViewModel() {
    private var _loginLiveData = MutableLiveData<Resource<List<Ads>>>()
    val loginLiveData: LiveData<Resource<List<Ads>>> get() = _loginLiveData

    private val _recyclerLiveDataError = MutableLiveData<String>()
    val recyclerLiveDataError: LiveData<String> get() = _recyclerLiveDataError


//    init {
//        getAllAds()
//    }

//    fun getAllAds() = viewModelScope.launch(Dispatchers.IO){
//        adRepo.getAllAds().collectLatest {
//            when(it.status){
//                Status.LOADING->{}
//                Status.SUCCESS->{
//                    _loginLiveData.postValue(it.data!!)
//                }
//                Status.ERROR->{
//                    _recyclerLiveDataError.postValue(it.message.toString())
//                }
//            }
//        }
//    }

    fun getAllAds() {
        _loginLiveData = adRepo.getAllAds() as MutableLiveData<Resource<List<Ads>>>
    }
}