package com.example.threadclone.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.example.threadclone.Model.HourlyCheckInCount
import com.example.threadclone.item_view.ThreadItem
import com.example.threadclone.roomdb.Dao
import com.example.threadclone.roomdb.Databse
import com.example.threadclone.roomdb.MemberStatusEntity
import com.example.threadclone.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

@Composable
fun Home(navHostController: NavHostController){
    val context= LocalContext.current
    val dao: Dao = Databse.getDatabaseInstance(context).getDao()

    val dummyMemberStatus1 = MemberStatusEntity(
        checkInTime = "21:00:00", // Insert your desired check-in time here
        checkOutTime = "14:00:00", // Insert your desired check-out time here
        status = "Present" // Insert your desired status here
    )
    val dummyMemberStatus2 = MemberStatusEntity(
        checkInTime = "22:00:00", // Insert your desired check-in time here
        checkOutTime = "2024-03-31 14:00:00", // Insert your desired check-out time here
        status = "Present" // Insert your desired status here
    )

    val dummyMemberStatus3 = MemberStatusEntity(
        checkInTime = "23:00:00", // Insert your desired check-in time here
        checkOutTime = "10:00:00", // Insert your desired check-out time here
        status = "Present" // Insert your desired status here
    )
    val dummyMemberStatus4 = MemberStatusEntity(
        checkInTime = "24:00:00", // Insert your desired check-in time here
        checkOutTime = "14:00:00", // Insert your desired check-out time here
        status = "Present" // Insert your desired status here
    )
//    LaunchedEffect(Unit) {
//        dao.insertMemberStatus(dummyMemberStatus1)
//        dao.insertMemberStatus(dummyMemberStatus2)
//        dao.insertMemberStatus(dummyMemberStatus3)
//        dao.insertMemberStatus(dummyMemberStatus4)
//    }
    val homeViewModel:HomeViewModel= viewModel()
    val threadAndUser by homeViewModel.threadsAndUsers.observeAsState()
    LazyColumn {
        items(threadAndUser?: emptyList()){pairs->
            ThreadItem(thread=pairs.first, user=pairs.second,navHostController=navHostController, userId =FirebaseAuth.getInstance().currentUser!!.uid )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
//    Home()
}