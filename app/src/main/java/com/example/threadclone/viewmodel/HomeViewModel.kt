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

class HomeViewModel : ViewModel() {

    val db=FirebaseDatabase.getInstance()
    val thread=db.getReference("threads")

    private var _threadsAndUsers=MutableLiveData<List<Pair<ThreadModel,UserModel>>>()
    val threadsAndUsers:LiveData<List<Pair<ThreadModel,UserModel>>> = _threadsAndUsers

    init {
        fetchThreadAndUser{
            _threadsAndUsers.value=it
        }
    }
    fun fetchThreadAndUser(onResult: (List<Pair<ThreadModel,UserModel>>) -> Unit){
        thread.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                val result= mutableListOf<Pair<ThreadModel,UserModel>>()
                for (threads in snapshot.children){
                    val thread=threads.getValue(ThreadModel::class.java)
                    thread.let {
                        fetchUserFromThread(it!!){
                            user->
                            result.add(0, (it to user))
                            if (result.size==snapshot.childrenCount.toInt()){
                                onResult(result)
                            }
                        }
                    }
                }
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