package com.example.eventer.ui

import android.annotation.SuppressLint
import android.provider.ContactsContract.Profile
import androidx.compose.material3.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.Routes
import com.example.eventer.NavItem
import com.example.eventer.R
import com.example.eventer.ui.theme.firasans
import kotlinx.coroutines.selects.select


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController) {

     /*val nl = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Profile", Icons.Default.Person),
        NavItem("Options", Icons.Default.List)
    )*/

    /*var selindex by remember { mutableStateOf(0)}

    when(selindex){
        0 -> Home(navController)
        1 -> Profile(navController)
        2 -> Options(navController)
    }*/

    //var selindex by remember { mutableStateOf(false)}

    Scaffold(
        bottomBar = {
                NavigationBar() {
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(Routes.third) }, icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text(text = "Home") })

                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(Routes.fourth) }, icon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile"
                            )
                        },
                        label = { Text(text = "Profile") })

                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(Routes.fifth) }, icon = {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "Options"
                            )
                        },
                        label = { Text(text = "Options") })
                }
            },
        topBar = { Topapp() },
        floatingActionButton = { But() },
    ) { innerPadding ->
        Cad(modifier = Modifier.padding(top = 40.dp))
    }
}

@Composable
fun But() {
    FloatingActionButton(onClick = { }) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
}

/*@Composable
fun Bot() {
    var selt by remember { mutableStateOf(0) }
    NavigationBar(modifier = Modifier.height(120.dp)) {
        nl.forEachIndexed() { index, navItem ->
            NavigationBarItem(
                selected = selt == index,
                onClick = {  },
                icon = { Icon(imageVector = navItem.icon, contentDescription = "null") },
                label = {
                    Text(text = navItem.label)
                })
        }
    }
     BottomAppBar(modifier = Modifier.height(110.dp), content = {
         Row(
             modifier = Modifier.fillMaxWidth(1f),
             horizontalArrangement = Arrangement.SpaceEvenly,
             verticalAlignment = Alignment.CenterVertically
         ) {
             //Text(text = "Laude")

             // Home Button
             IconButton(onClick = { /*TODO*/ }) {
                 Icon(Icons.Default.Home, contentDescription = "Home")
             }
             Spacer(modifier = Modifier.width(2.dp))
             // Options Button
             IconButton(onClick = { /*TODO*/ }) {
                 Icon(Icons.Default.List, contentDescription = "Options")
             }
             Spacer(modifier = Modifier.width(2.dp))
             // Profile Button
             IconButton(onClick = { /*TODO*/ }) {
                 Icon(Icons.Default.Person, contentDescription = "Profile")
             }
         }
     })
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topapp() {
    TopAppBar(modifier = Modifier
        .height(110.dp)
        .padding(),
        title = {
            Text(
                text = "Welcome",
                fontSize = 38.sp,
                fontFamily = firasans,
                fontWeight = FontWeight.SemiBold
            )
        })
    Spacer(modifier = Modifier.height(25.dp))
}

@Composable
fun Cad(modifier: Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        ElevatedCard(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp)
                .height(200.dp)
        ) {
            Image(
                modifier = Modifier,
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.party),
                contentDescription = "Home page Image"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "''Text Here''",
            fontSize = 22.sp,
            textAlign = Center,
            fontStyle = FontStyle.Italic
        )

        ElevatedCard(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .height(200.dp)
        ) {
            Image(
                modifier = Modifier,
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.party),
                contentDescription = "Home page Image"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "''Text Here''",
            fontSize = 22.sp,
            textAlign = Center,
            fontStyle = FontStyle.Italic
        )

        ElevatedCard(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .height(200.dp)
        ) {
            Image(
                modifier = Modifier,
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.party),
                contentDescription = "Home page Image"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "''Text Here''",
            fontSize = 22.sp,
            textAlign = Center,
            fontStyle = FontStyle.Italic
        )

    }
}