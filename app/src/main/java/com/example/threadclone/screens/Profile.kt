package com.example.threadclone.screens

import android.content.pm.PackageManager
import android.provider.ContactsContract.Profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threadclone.Navigation.Routes
import com.example.threadclone.R
import com.example.threadclone.Utils.SharedPref
import com.example.threadclone.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Profile(navController:NavHostController){
    val authViewModel: AuthViewModel = viewModel()
    val context= LocalContext.current
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    LaunchedEffect(firebaseUser) {
        if (firebaseUser==null){
            navController.navigate(Routes.Login.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }
    }

    LazyColumn {
        item {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val(text,logo,userImg,userName,bio,followers,following, )=createRefs()


                Text(text = SharedPref.getName(context), style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp
                ), modifier = Modifier
                    .constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(top = 10.dp, start = 10.dp)
                )

                Text(text = SharedPref.getUserName(context), style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp
                ), modifier = Modifier
                    .constrainAs(userName) {
                        top.linkTo(text.top)
                        start.linkTo(parent.end, margin = 8.dp)
                    }
                    .padding(top = 10.dp, start = 10.dp)
                )


                Image(painter = rememberAsyncImagePainter(model = SharedPref.getImageUrl(context)), contentDescription = "close",
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(parent.top, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .size(60.dp)
                        .clip(CircleShape)
                        .clickable {
                            //TODO
                        }, contentScale = ContentScale.Crop)

                Text(text = SharedPref.getBio(context), style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp
                ), modifier = Modifier
                    .constrainAs(bio) {
                        top.linkTo(userName.bottom)
                        start.linkTo(parent.start, margin = 8.dp)
                    }
                    .padding(top = 10.dp, start = 10.dp)
                )

                Text(text = SharedPref.getUserName(context), style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp
                ), modifier = Modifier
                    .constrainAs(followers) {
                        top.linkTo(bio.bottom)
                        start.linkTo(parent.start, margin = 8.dp)
                    }
                    .padding(top = 10.dp, start = 10.dp)
                )

                Text(text = SharedPref.getUserName(context), style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp
                ), modifier = Modifier
                    .constrainAs(following) {
                        top.linkTo(followers.bottom)
                        start.linkTo(parent.start, margin = 8.dp)
                    }
                    .padding(top = 10.dp, start = 10.dp)
                )

                ElevatedButton(onClick = { authViewModel.logout() }) {
                    Text(text ="Logout")
                }


            }

        }
    }


}