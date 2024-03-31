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

class SearchViewModel : ViewModel() {

    val db=FirebaseDatabase.getInstance()
    val users=db.getReference("users")

    private var _Users=MutableLiveData<List<UserModel>>()
    val Users:LiveData<List<UserModel>> = _Users

    init {
        fetchUsers{
            _Users.value=it
        }
    }
    fun fetchUsers(onResult: (List<UserModel>) -> Unit){
        users.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                val result= mutableListOf<UserModel>()
                for (threads in snapshot.children){
                    val thread=threads.getValue(UserModel::class.java)
                   result.add(thread!!)
                }
                onResult(result)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchUserFromThread(thread: ThreadModel,onResult:(UserModel)->Unit){
        db.getReference("users").child(thread.userId)
            .addListenerForSingleValueEvent(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    val user=snapshot.getValue(UserModel::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }
}