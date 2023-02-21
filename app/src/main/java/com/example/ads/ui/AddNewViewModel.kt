package com.example.ads.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ads.model.Ads
import com.example.ads.model.User
import com.example.ads.repositories.AdRepo
import com.example.ads.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewViewModel @Inject constructor(val adRepo: AdRepo):ViewModel() {


    fun upload(ads: Ads, imgUri: Uri) = viewModelScope.launch(Dispatchers.IO){
        adRepo.uploadAdToFirebase(ads,imgUri)
    }
}