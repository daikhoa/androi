package com.example.cuoiki.Trang.Danhmuc

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Csdl.Danhmuc
import com.example.cuoiki.Viewmodel.Danhmucviewmodel
import com.example.cuoiki.Viewmodel.dangnhapviewmodel

@Composable
fun Themdm(navController: NavController) {
    val viewmodel : Danhmucviewmodel = viewModel()
    var danhMuc by remember {  mutableStateOf("") }
    val authViewModel: dangnhapviewmodel = viewModel()
    val context = LocalContext.current
    val currentUser by authViewModel.currentUser.collectAsState()

    // Kiểm tra đăng nhập và quyền admin
    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate("dangnhap") {
                popUpTo("themdm") { inclusive = true }
            }
        } else if (!authViewModel.isAdmin()) {
            Toast.makeText(context, "Chỉ admin được thêm danh mục", Toast.LENGTH_SHORT).show()
            navController.navigate("chonban") {
                popUpTo("themdm") { inclusive = true }
            }
        }
    }

    // Chỉ hiển thị nếu là admin
    if (currentUser != null && authViewModel.isAdmin()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Thêm Danh Mục Mới", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = danhMuc,
                onValueChange = {danhMuc = it },
                label = { Text("Tên danh mục") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (danhMuc.isNotEmpty()) {
                        viewmodel.them(Danhmuc(tendanhmuc = danhMuc))
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lưu")
            }


            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Quay lại")
            }
        }


    }

}
