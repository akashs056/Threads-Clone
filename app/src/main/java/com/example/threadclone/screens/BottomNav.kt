package com.example.threadclone.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.threadclone.Model.BottomNavItem
import com.example.threadclone.Navigation.Routes

@Composable
fun BottomNav(naveController : NavHostController){

    val navController1 = rememberNavController()

    Scaffold(bottomBar = { MyBottomBar(navController1) }) {innerPadding->
        NavHost(navController = navController1, startDestination = Routes.Home.routes ,
            modifier = Modifier.padding(innerPadding)){

            composable(Routes.Home.routes){
                Home(naveController)
            }
            composable(Routes.Notification.routes){
                Notification()
            }
            composable(Routes.Search.routes){
                Search(naveController)
            }
            composable(Routes.AddThread.routes){
                AddThreads(navController1)
            }
            composable(Routes.Profile.routes){
                Profile(naveController)
            }
        }
    }

}

@Composable
fun MyBottomBar(naveController: NavHostController) {

    val backStackEntry=naveController.currentBackStackEntryAsState()

    val list= listOf(

        BottomNavItem(
            "Home",
            Routes.Home.routes,
            Icons.Rounded.Home
        ),
        BottomNavItem(
            "Search",
            Routes.Search.routes,
            Icons.Rounded.Search
        ),
        BottomNavItem(
            "AddThreads",
            Routes.AddThread.routes,
            Icons.Rounded.Add
        ),
        BottomNavItem(
            "Notification",
            Routes.Notification.routes,
            Icons.Rounded.Notifications
        ),
        BottomNavItem(
            "Profile",
            Routes.Profile.routes,
            Icons.Rounded.Person
        )
    )

    BottomAppBar {
        list.forEach{
            val selected=it.route== backStackEntry.value?.destination?.route
            
            NavigationBarItem(selected = selected,
                onClick = { naveController.navigate(it.route){
                    popUpTo(naveController.graph.findStartDestination().id){
                        saveState=true
                    }
                    launchSingleTop=true
                } },
                icon = { androidx.compose.material3.Icon(imageVector = it.icon, contentDescription = it.title) })
        }
    }

}
