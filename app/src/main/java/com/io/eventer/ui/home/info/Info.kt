package com.io.eventer.ui.home.info

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.io.eventer.ui.theme.firasans
import androidx.core.net.toUri
import androidx.navigation.NavController

@Composable
fun Info(navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.TwoTone.Info,
            contentDescription = "Info Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp).padding(top = 20.dp)
        )
        Text(
            text = "About Eventers",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontFamily = firasans
        )

        Text(
            text = "Eventers is a simple and evolving event management application developed as part of an ongoing project. It aims to make organizing and managing events easier and more efficient, whether for small gatherings or larger functions. This app is actively being improved, with regular updates planned to enhance existing features and introduce new ones based on user feedback. As a growing project, we welcome ideas and suggestions from users to help shape the app’s future. Your input plays a key role in making Eventers better with each update.",
            fontFamily = firasans,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Developer Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            TextButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://github.com/krtonia".toUri())
                context.startActivity(intent)
            }) {
                Text(
                    text = "Shikhar Tiwari",
                    fontFamily = firasans,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
