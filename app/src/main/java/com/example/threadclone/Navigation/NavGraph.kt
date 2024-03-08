package com.example.threadclone.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(navController = navController,
        startDestination = Routes.Splash.routes){

    }

}