package com.example.cuoiki.Trang.Danhmuc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Csdl.Danhmuc
import com.example.cuoiki.Viewmodel.Danhmucviewmodel

@Composable
fun Themdm(navController: NavController) {
    val viewmodel : Danhmucviewmodel = viewModel()
    var danhMuc by remember {  mutableStateOf("") }

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
