package com.example.cuoiki.Trang

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Viewmodel.Nhanvienviewmodel

@Composable
fun dangnhap(navController: NavController) {
    var taikhoan by remember { mutableStateOf("") }
    var matkhau by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val viewmodel: Nhanvienviewmodel = viewModel()
    val nhanvien by viewmodel.nhanvien.collectAsStateWithLifecycle(initialValue = emptyList())


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Đăng nhập",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )


                OutlinedTextField(
                    value = taikhoan,
                    onValueChange = { taikhoan = it },
                    label = { Text("Tài khoản") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )


                OutlinedTextField(
                    value = matkhau,
                    onValueChange = { matkhau = it },
                    label = { Text("Mật khẩu") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                // Thông báo lỗi
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                // Nút Đăng nhập
                Button(
                    onClick = {
                        if (taikhoan.isBlank() || matkhau.isBlank()) {
                            errorMessage = "Vui lòng nhập đầy đủ thông tin"
                        }
                        else{
                            var check = nhanvien.find { it.taikhoan == taikhoan && it.mk==matkhau }
                            if (check == null){
                                errorMessage = "mời bạn đăng nhập lại"
                            }
                            else{
                                navController.navigate("chonban")
                            }

                        }
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Đăng nhập", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    )
}

