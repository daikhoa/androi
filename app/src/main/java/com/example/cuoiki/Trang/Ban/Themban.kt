package com.example.cuoiki.Trang.Ban

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
import com.example.cuoiki.Csdl.Ban
import com.example.cuoiki.Viewmodel.Banviewmodel
import com.example.cuoiki.Viewmodel.dangnhapviewmodel

@Composable
fun Themban(navController: NavController) {
    val viewmodel : Banviewmodel = viewModel()
    var soban by remember {  mutableStateOf("") }

    val authViewModel: dangnhapviewmodel = viewModel()
    val context = LocalContext.current
    val currentUser by authViewModel.currentUser.collectAsState()

    // Kiểm tra đăng nhập và quyền admin
    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate("dangnhap") {
                popUpTo("themban") { inclusive = true }
            }
        } else if (!authViewModel.isAdmin()) {
            Toast.makeText(context, "Chỉ admin được thêm bàn", Toast.LENGTH_SHORT).show()
            navController.navigate("chonban") {
                popUpTo("themban") { inclusive = true }
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
            Text(text = "Thêm Bàn Mới", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = soban,
                onValueChange = {if(it.toIntOrNull()!= null) {soban =it}  },
                label = { Text("Số bàn") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (soban.isNotEmpty()) {
                        var soBan=soban.toIntOrNull()
                        if(soBan !=null){
                            viewmodel.them(Ban(soban = soBan))
                            navController.popBackStack()}
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
