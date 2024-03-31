package com.example.threadclone.Utils

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.core.Context

object SharedPref {
    @SuppressLint("CommitPrefEdits")
    fun storeData(name:String, email:String, bio:String, uid:String, userName:String, imageUrl:String, context:android.content.Context){
        val sharedPref=context.getSharedPreferences("users",MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putString("name",name)
        editor.putString("email",email)
        editor.putString("bio",bio)
        editor.putString("uid",uid)
        editor.putString("userName",userName)
        editor.putString("imageUrl",imageUrl)
        editor.apply()
    }
    fun  getUserName(context: android.content.Context):String{
        val sharedPreferences=context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("userName","")!!
    }

    fun  getEmail(context: android.content.Context):String{
        val sharedPreferences=context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("email","")!!
    }

    fun  getBio(context: android.content.Context):String{
        val sharedPreferences=context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("bio","")!!
    }

    fun  getName(context: android.content.Context):String{
        val sharedPreferences=context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("name","")!!
    }

    fun  getImageUrl(context: android.content.Context):String{
        val sharedPreferences=context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("imageUrl","")!!
    }
}