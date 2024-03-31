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

class UserViewModel : ViewModel() {

    val db=FirebaseDatabase.getInstance()
    val threadRefs=db.getReference("threads")
    val userRefs=db.getReference("users")

    private val _threads=MutableLiveData(listOf<ThreadModel>())




}