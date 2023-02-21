package com.example.ads.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ads.model.Ads
import com.example.ads.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class AdRepo @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val context: Context,
    private val storage: FirebaseStorage
) {

    fun loginUser(email:String ,password:String):Flow<Resource<String>>{
        val mutableLivedata = MutableStateFlow<Resource<String>>(Resource.loading(null))
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) mutableLivedata.value = Resource.success("Login Success")
            else mutableLivedata.value =
                Resource.error(null, it.exception?.localizedMessage.toString())
        }
        return mutableLivedata
    }

    suspend fun uploadAdToFirebase(ad: Ads,  imgUri: Uri) = withContext(Dispatchers.IO){
        ad.id = firebaseAuth.currentUser!!.uid + Calendar.getInstance().timeInMillis
        firebaseDatabase.getReference("ads")
            .child(ad.id!!)
            .setValue(ad).addOnSuccessListener {
            }.addOnFailureListener {
                Log.e( "uploadAdToFirebase: ",it.toString() )
            }


        uploadImg(imgUri, ad)

    }


    private fun uploadImg(imgUri: Uri, ad: Ads) {
        storage.getReference("coverImg/" + UUID.randomUUID()).putFile(imgUri)
            .onSuccessTask { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    firebaseDatabase.getReference("ads")
                        .child(ad.id!!)
                        .child("imgURL").setValue(it.toString())
                }.addOnFailureListener {

                }
            }.addOnFailureListener {
            }
    }

    fun getAllAds():LiveData<Resource<List<Ads>>>{
        val result = MutableLiveData<Resource<List<Ads>>>(Resource.loading(null))

        firebaseDatabase.getReference("ads")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Ads>()

                    snapshot.children.forEach {
                        list.add(it.getValue(Ads::class.java)!!)
                    }

                    if (list.isNotEmpty()) {
                        result.value = Resource.success(list.toList())
                    } else {
                        result.value = Resource.error(null,"there's no data to show")
                    }
                    Log.e("onDataChange: ", snapshot.value.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("onCancelled: ", error.message)
                    result.value = Resource.error(null, error.message)
                }

            })
        return result
    }
}