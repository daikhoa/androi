package com.example.cuoiki.Trang.Danhmuc


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Viewmodel.Danhmucviewmodel
import com.example.cuoiki.Viewmodel.dangnhapviewmodel

@Composable
fun Danhsachdm(navController: NavController) {
    val viewmodel : Danhmucviewmodel = viewModel()
    val danhmuc by viewmodel.danhmuc.collectAsStateWithLifecycle(initialValue = emptyList())
    var dsdanhmuc = danhmuc
    val authViewModel: dangnhapviewmodel = viewModel()
    val context = LocalContext.current
    val currentUser by authViewModel.currentUser.collectAsState()

    // Kiểm tra đăng nhập và quyền admin
    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate("dangnhap") {
                popUpTo("danhsachdm") { inclusive = true }
            }
        } else if (!authViewModel.isAdmin()) {
            Toast.makeText(context, "Chỉ admin được xem danh mục", Toast.LENGTH_SHORT).show()
            navController.navigate("chonban") {
                popUpTo("danhsachdm") { inclusive = true }
            }
        }
    }

    // Chỉ hiển thị nếu là admin
    if (currentUser != null && authViewModel.isAdmin()) {
        Scaffold(
            floatingActionButton = {
                Button(onClick = {navController.navigate("Themdm")}) {
                    Text("Thêm danh mục") // hoặc Icon nếu thích
                }
            },
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate("Menu") }
                    )
                }
            }
        ) { padding ->
            LazyColumn(contentPadding = padding) {
                items(dsdanhmuc) { danhmuc ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),


                        ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(" ${danhmuc.tendanhmuc}", style = MaterialTheme.typography.titleMedium)
                            Row {
                                Button(
                                    onClick = { navController.navigate("Suadm/${danhmuc.iddanhmuc}")},
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text("Sửa")
                                }
                                Button(
                                    onClick = { viewmodel.xoa(danhmuc) },
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
}


