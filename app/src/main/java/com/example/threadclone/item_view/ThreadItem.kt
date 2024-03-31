package com.example.threadclone.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threadclone.Model.ThreadModel
import com.example.threadclone.Model.UserModel
import com.example.threadclone.Navigation.Routes
import com.example.threadclone.R
import com.example.threadclone.Utils.SharedPref

@Composable
fun ThreadItem(
    thread: ThreadModel,
    user:UserModel,
    navHostController: NavHostController,
    userId:String
){
    Column {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)){
            val(userImage,userName,daye,time,title,image)=createRefs()
            Image(painter = rememberAsyncImagePainter(model = user.imageUrl), contentDescription = "close",
                modifier = Modifier
                    .constrainAs(userImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable {
                        //TODO
                    }, contentScale = ContentScale.Crop)

            Text(text =user.userName, style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            ), modifier = Modifier
                .constrainAs(userName) {
                    top.linkTo(userImage.top)
                    start.linkTo(userImage.end, margin = 8.dp)
                    bottom.linkTo(userImage.bottom, margin = 5.dp)
                }
                .padding(top = 10.dp, start = 10.dp)
            )
            Text(text = thread.thread, style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            ), modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(userName.bottom)
                    start.linkTo(userName.start, margin = 50.dp)
                    end.linkTo(parent.end, margin = 50.dp)
                }
                .fillMaxWidth()
                .padding(top = 12.dp, start = 10.dp, end = 14.dp)
            )


            if (thread.image!="") {
                Card(modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(title.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                    Image(
                        painter = rememberAsyncImagePainter(model = thread.image),
                        contentDescription = "close",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }

}
@Preview(showBackground = true)
@Composable
fun ThreadItemPreview(){
//    ThreadItem()
}