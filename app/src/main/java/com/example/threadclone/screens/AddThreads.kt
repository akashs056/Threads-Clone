package com.example.threadclone.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.threadclone.viewmodel.AddThreadsViewModel
import com.example.threadclone.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AddThreads(navHostController: NavHostController){
    val context= LocalContext.current
    val addThreadViewModel:AddThreadsViewModel= viewModel()
    val isPosted by addThreadViewModel.isPosted.observeAsState(false)

    var thread by remember {
        mutableStateOf("")
    }

    var imageUri by remember{
        mutableStateOf<Uri?>(null)
    }

    LaunchedEffect(key1 = isPosted) {
        if (isPosted!!){
            thread=""
            imageUri=null
            Toast.makeText(context,"Thread Added",Toast.LENGTH_SHORT).show()

            navHostController.navigate(Routes.Home.routes){
                popUpTo(Routes.AddThread.routes){
                    inclusive=true
                }
            }
        }
    }

//    val authViewModel: AuthViewModel = viewModel()
//    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val permissionToRequest=if (Build.VERSION.SDK>= Build.VERSION_CODES.TIRAMISU.toString()){
        Manifest.permission.READ_MEDIA_IMAGES
    }else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val launcher= rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            uri:Uri?->
        imageUri=uri
    }

    val permissionLauncher= rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            isGranted:Boolean->
        if (isGranted){
            Toast.makeText(context,"Permission Granted", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val(closeBtn,addThreadsTxt,userImg,userName,addThreadsBtn,imageShowBox,threadEditTest,
            anyoneTxt,postBth)=createRefs()

        Image(painter = painterResource(id = R.drawable.close), contentDescription = "close",
            modifier = Modifier
                .constrainAs(closeBtn) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(top = 10.dp, start = 10.dp)
                .clickable {
                    navHostController.navigate(Routes.Home.routes){
                        popUpTo(Routes.AddThread.routes){
                            inclusive=true
                        }
                    }
                })

        Text(text = "Add Threads", style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp
        ), modifier = Modifier
            .constrainAs(addThreadsTxt) {
                top.linkTo(closeBtn.top)
                start.linkTo(closeBtn.end)
                bottom.linkTo(closeBtn.bottom)
            }
            .padding(top = 10.dp, start = 10.dp)
        )

        Image(painter = rememberAsyncImagePainter(model = SharedPref.getImageUrl(context)), contentDescription = "close",
            modifier = Modifier
                .constrainAs(userImg) {
                    top.linkTo(addThreadsTxt.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
                .size(40.dp)
                .clip(CircleShape)
                .clickable {
                    //TODO
                }, contentScale = ContentScale.Crop)

        Text(text = SharedPref.getName(context), style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp
        ), modifier = Modifier
            .constrainAs(userName) {
                top.linkTo(userImg.top)
                start.linkTo(userImg.end, margin = 8.dp)
                bottom.linkTo(userImg.bottom, margin = 5.dp)
            }
            .padding(top = 10.dp, start = 10.dp)
        )

        BasicTextWithHint(hint = "Start a Thread...", value =thread , onValueChange ={thread=it} , modifier =
        Modifier
            .constrainAs(threadEditTest) {
                top.linkTo(userName.bottom, margin = 8.dp)
                start.linkTo(userName.start, margin = 50.dp)
                end.linkTo(parent.end)
            }
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .fillMaxWidth())

        Image(painter =painterResource(id = R.drawable.attach),
            contentDescription = "close",
            modifier = Modifier
                .constrainAs(addThreadsBtn) {
                    top.linkTo(threadEditTest.bottom)
                    start.linkTo(threadEditTest.start)
                }
                .wrapContentSize()
                .padding(top = 6.dp, start = 10.dp)
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

        Image(painter = rememberAsyncImagePainter(model = imageUri), contentDescription = "close",
            modifier = Modifier
                .constrainAs(imageShowBox) {
                    top.linkTo(addThreadsBtn.bottom)
                    start.linkTo(addThreadsBtn.start)
                }
                .size(300.dp)
                .padding(top = 1.dp, start = 10.dp)
                .clickable {
                    //TODO
                })

        Text(text = "Anyone can reply", style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ), modifier = Modifier
            .constrainAs(anyoneTxt) {
                start.linkTo(parent.start, margin = 10.dp)
                bottom.linkTo(parent.bottom, margin = 20.dp)
            }
            .padding(top = 10.dp, start = 10.dp)
        )

        Button(
            onClick = {
                    if (imageUri==null){
                        addThreadViewModel.saveData(thread,FirebaseAuth.getInstance().currentUser!!.uid.toString(),"")
                    }else{
                        addThreadViewModel.saveImage(thread, imageUri!!,FirebaseAuth.getInstance().currentUser!!.uid)
                    }
            },
            modifier = Modifier
                .constrainAs(postBth) {
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                }
                .width(100.dp)
                .height(45.dp)
            ,    colors = ButtonDefaults.buttonColors(
                if (thread.isNotEmpty()) Color.Blue else Color.Gray
            ),
            enabled = thread.isNotEmpty(),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = "Post",
                color = Color.White,
                fontSize = 20.sp
            )
        }
        
    }

}
@Composable
fun BasicTextWithHint(hint:String,value:String,onValueChange: (String) -> Unit,
                      modifier: Modifier){
    Box(modifier=modifier) {
        if (value.isEmpty()){
            Text(text = hint, color = Color.Gray)
        }

        BasicTextField(value = value, onValueChange = onValueChange,
            textStyle = TextStyle.Default.copy(color= Color.Black, fontSize = 18.sp),
            modifier=Modifier.fillMaxWidth()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun AddThreadsPreview(){
//    AddThreads()
}