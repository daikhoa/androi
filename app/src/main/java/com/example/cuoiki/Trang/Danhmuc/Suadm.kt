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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Csdl.Danhmuc
import com.example.cuoiki.Viewmodel.Danhmucviewmodel

@Composable
fun Suadm(navController: NavController, id : Int) {
    val viewmodel : Danhmucviewmodel = viewModel()
    val danhmuc by viewmodel.danhmuc.collectAsStateWithLifecycle(initialValue = emptyList())
    val dsdanhmuc= danhmuc.find { it.iddanhmuc == id }

    if (dsdanhmuc == null) {
        Text("Liên hệ không tồn tại!", fontSize = 20.sp, color = MaterialTheme.colorScheme.error)
        return
    }

    var tendanhmuc by remember { mutableStateOf(dsdanhmuc.tendanhmuc) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Thêm Bàn Mới", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = tendanhmuc,
            onValueChange = {tendanhmuc = it

            },
            label = { Text("Số bàn") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewmodel.sua(Danhmuc(iddanhmuc = id, tendanhmuc = tendanhmuc))
                navController.popBackStack()
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
