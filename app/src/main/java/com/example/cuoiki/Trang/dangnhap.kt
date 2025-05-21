package com.example.cuoiki.Trang

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Viewmodel.dangnhapviewmodel

@Composable
fun dangnhap(navController: NavController) {
    val viewModel: dangnhapviewmodel = viewModel()
    val context = LocalContext.current
    var taikhoan by remember { mutableStateOf(TextFieldValue("")) }
    var matkhau by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }
    val currentUser by viewModel.currentUser.collectAsState()

    // Điều hướng khi đăng nhập thành công
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate("chonban") {
                popUpTo("dangnhap") { inclusive = true }
            }
        }
    }

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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
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
                        if (taikhoan.text.isBlank() || matkhau.text.isBlank()) {
                            errorMessage = "Vui lòng nhập đầy đủ thông tin"
                        } else {
                            viewModel.login(taikhoan.text, matkhau.text)
                            if (currentUser == null) {
                                errorMessage = "Tài khoản hoặc mật khẩu không đúng"
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