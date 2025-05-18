package com.example.cuoiki.Trang.Kinhdoanh

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Viewmodel.Banviewmodel

@Composable
fun Chonban(navController: NavController) {
    val viewmodel :Banviewmodel = viewModel()
    val ban by viewmodel.ban.collectAsStateWithLifecycle(initialValue = emptyList())
    var dsban = ban.toList()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Danh sách bàn", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(dsban) { ban ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("Donhang/${ban.idban}")
                        },
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Text(text = ban.soban.toString(), fontSize = 18.sp)
                    }
                }
            }
        }
    }

}


