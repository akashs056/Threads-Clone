package com.example.threadclone.screens

import android.provider.ContactsContract.Profile
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.example.threadclone.Navigation.Routes
import com.example.threadclone.viewmodel.AuthViewModel

@Composable
fun Profile(navController:NavHostController){
    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    LaunchedEffect(firebaseUser) {
        if (firebaseUser==null){
            navController.navigate(Routes.Login.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }
    }

    Text(text = "Profile", modifier = Modifier.clickable {
        authViewModel.logout()
    })

}