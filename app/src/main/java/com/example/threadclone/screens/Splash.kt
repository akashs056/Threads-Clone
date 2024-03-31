package com.example.threadclone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.threadclone.Navigation.Routes
import com.example.threadclone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavHostController){

    ConstraintLayout(modifier = Modifier.fillMaxSize()){
        val (image)=createRefs()
        Image(painter = painterResource(id = R.drawable.threadslogo), contentDescription = "logo",
            modifier = Modifier.constrainAs(image){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.size(120.dp))


    }

    LaunchedEffect(true) {
        delay(3000)
        if (FirebaseAuth.getInstance().currentUser==null){
            navController.navigate(Routes.Login.routes){
                popUpTo(navController.graph.startDestinationId){
                    inclusive=true
                }
                launchSingleTop=true
            }
        }else{
            navController.navigate(Routes.BottomNav.routes)
        }
    }

}