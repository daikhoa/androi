package com.example.cuoiki.Trang.Ban

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Csdl.Ban
import com.example.cuoiki.Viewmodel.Banviewmodel

@Composable
fun Suaban(navController: NavController, id: Int) {
    val viewmodel : Banviewmodel = viewModel()
    val ban by viewmodel.ban.collectAsStateWithLifecycle(initialValue = emptyList())
    val Ban= ban.find { it.idban == id }

    if (Ban == null) {
        Text("Liên hệ không tồn tại!", fontSize = 20.sp, color = MaterialTheme.colorScheme.error)
        return
    }

    var soban by remember { mutableStateOf(Ban.soban.toString()) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Thêm Bàn Mới", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = soban,
                onValueChange = {

                        soban = it

                },
                label = { Text("Số bàn") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (soban!=null) {
                        var soBan = soban.toIntOrNull()
                        if (soBan != null){
                            viewmodel.sua(Ban(idban = id, soban = soBan))
                            navController.popBackStack()
                        }
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


