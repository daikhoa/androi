package com.example.cuoiki.Trang.Ban

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Viewmodel.Banviewmodel

@Composable
fun Danhsachban(navController: NavController) {
    val viewmodel : Banviewmodel = viewModel()
    val Ban by viewmodel.ban.collectAsStateWithLifecycle(initialValue = emptyList())
    var dsban = Ban

    Scaffold(
        floatingActionButton = {
            Button(onClick = {navController.navigate("Themban")}) {
                Text("Thêm bàn") // hoặc Icon nếu thích
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(dsban) { ban ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Bàn ${ban.soban}", style = MaterialTheme.typography.titleMedium)
                        Row {
                            Button(
                                onClick = { navController.navigate("Suaban/${ban.idban}")},
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text("Sửa")
                            }
                            Button(
                                onClick = { viewmodel.xoa(ban) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("Xóa", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}




