package com.example.ads.di


import android.content.Context
import com.example.ads.repositories.AdRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun repo(  firebaseDatabase: FirebaseDatabase,
    firebaseAuth: FirebaseAuth ,@ApplicationContext context: Context , storage: FirebaseStorage):AdRepo{
        return AdRepo( firebaseDatabase , firebaseAuth  , context,storage)
    }

    @Provides
    fun firebaseAuthProvider():FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
    @Provides
    fun firebaseDatabaseProvider():FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }
    @Provides
    @Singleton
    fun firebaseStoregeProvider():FirebaseStorage{
        return FirebaseStorage.getInstance()
    }





}