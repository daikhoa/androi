@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cuoiki.Trang.Kinhdoanh


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Viewmodel.Donhangviewmodel

@Composable
fun Donhang(navController: NavController, idban: Int) {
    val viewmodel: Donhangviewmodel = viewModel()
    val donhang by viewmodel.donhang.collectAsStateWithLifecycle(initialValue = emptyList())
    val dsdonhang = donhang.filter { it.idban == idban }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Đơn hàng - Bàn $idban") },
                navigationIcon = {
                    Button(
                        onClick = { navController.navigate("Chonban") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Quay lại", color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            Button(onClick = { navController.navigate("chonmon/$idban") }) {
                Text("Thêm món")
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(dsdonhang) { donhang ->
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
                        Text(
                            "${donhang.tensp} \n Số lượng: ${donhang.soluong}\n Giá: ${donhang.giasp}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row {
                            Button(
                                onClick = { viewmodel.xoa(donhang) },
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