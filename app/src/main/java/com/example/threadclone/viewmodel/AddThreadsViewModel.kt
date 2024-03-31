package com.example.threadclone.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threadclone.Model.ThreadModel
import com.example.threadclone.Model.UserModel
import com.example.threadclone.Utils.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class AddThreadsViewModel : ViewModel() {

    val db=FirebaseDatabase.getInstance()
    val userRef=db.getReference("threads")

    private val storageRef=Firebase.storage.reference
    private val imageRef=storageRef.child("threads/${ UUID.randomUUID()}.jpg")


    private val _isPosted=MutableLiveData<Boolean>()
    val isPosted:LiveData<Boolean> = _isPosted

    fun saveImage(
        thread: String,
        image: Uri,
        userId: String) {
        val uploadTask=imageRef.putFile(image!!)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(thread,userId,it.toString())
            }
        }
    }

    fun saveData(
        thread: String,
        userId: String,
        image: String,
    ) {
        val threadData=ThreadModel(thread,image,userId,System.currentTimeMillis().toString())
        userRef.push().setValue(threadData).addOnSuccessListener {
            _isPosted.postValue(true)
        }.addOnFailureListener{
            _isPosted.postValue(false)
        }
    }
}