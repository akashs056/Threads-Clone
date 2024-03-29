package com.example.threadclone.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threadclone.Navigation.Routes
import com.example.threadclone.R
import com.example.threadclone.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Register(navController: NavHostController){

    var email by remember{
        mutableStateOf("")
    }
    var name by remember{
        mutableStateOf("")
    }
    var username by remember{
        mutableStateOf("")
    }
    var bio by remember{
        mutableStateOf("")
    }
    var pass by remember{
        mutableStateOf("")
    }

    var imageUri by remember{
        mutableStateOf<Uri?>(null)
    }

    val authViewModel:AuthViewModel= viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val permissionToRequest=if (Build.VERSION.SDK>= Build.VERSION_CODES.TIRAMISU.toString()){
        Manifest.permission.READ_MEDIA_IMAGES
    }else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val launcher= rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        uri:Uri?->
        imageUri=uri
    }
    val context= LocalContext.current
    
    val permissionLauncher= rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        isGranted:Boolean->
        if (isGranted){
            Toast.makeText(context,"Permission Granted",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Permission Denied",Toast.LENGTH_SHORT).show()
        }
    }



    LaunchedEffect(firebaseUser) {
        if (firebaseUser!=null){
            navController.navigate(Routes.BottomNav.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(text = "Register Here", style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )
        )

        Box(modifier = Modifier.height(30.dp))

        Image(painter = if (imageUri==null) painterResource(id = R.drawable.profile)
            else rememberAsyncImagePainter(model = imageUri) , contentDescription ="proifle" ,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    val isGranted = ContextCompat.checkSelfPermission(
                        context, permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED
                    if (isGranted) {
                        launcher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permissionToRequest)
                    }

                }, contentScale = ContentScale.Crop)

        Box(modifier = Modifier.height(30.dp))

        OutlinedTextField(value = name, onValueChange = {name=it},
            label = {Text(text = "Name")  },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Box(modifier = Modifier.height(10.dp))


        OutlinedTextField(value = username, onValueChange = {username=it},
            label = {Text(text = "Username")  },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Box(modifier = Modifier.height(10.dp))


        OutlinedTextField(value = bio, onValueChange = {bio=it},
            label = {Text(text = "Bio")  },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Box(modifier = Modifier.height(10.dp))


        OutlinedTextField(value = email, onValueChange = {email=it},
            label = {Text(text = "Email")  },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Box(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = pass, onValueChange = {pass=it},
            label = {Text(text = "Password")  },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Box(modifier = Modifier.height(20.dp))

        ElevatedButton(onClick = {
                                 if (name.isEmpty()||email.isEmpty()||bio.isEmpty()||username.isEmpty()||pass.isEmpty()||imageUri==null){
                                     Toast.makeText(context,"Please provide all the details",Toast.LENGTH_SHORT).show()
                                     return@ElevatedButton
                                 }else{
                                     authViewModel.register(email,pass,name,bio,username,imageUri,context)
                                 }
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)) {
            Text(text = "Register",style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp), modifier = Modifier.padding(vertical = 6.dp))
        }

        TextButton(onClick = {
            navController.navigate(Routes.Login.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)) {
            Text(text = "Already have a Account ? Login",style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp)
            )
        }


    }

}

//@Preview(showBackground = true)
//@Composable
//fun RegisterView() {
//    Register()
//}
