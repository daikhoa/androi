package com.example.cuoiki.Trang.Nhanvien

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Csdl.Nhanvien
import com.example.cuoiki.Viewmodel.Nhanvienviewmodel
import com.example.cuoiki.Viewmodel.dangnhapviewmodel

@Composable
fun Suanv(navController: NavController, id: Int) {
    val viewModel: Nhanvienviewmodel = viewModel()
    val nhanVien by viewModel.nhanvien.collectAsStateWithLifecycle(initialValue = emptyList())
    val nhanVienItem = nhanVien.find { it.idnhanvien == id }

    if (nhanVienItem == null) {
        Text("Nhân viên không tồn tại!", fontSize = 20.sp, color = MaterialTheme.colorScheme.error)
        return
    }

    var tenNv by remember { mutableStateOf(nhanVienItem.tennv) }
    var sdt by remember { mutableStateOf(nhanVienItem.sdt.toString()) }
    var taiKhoan by remember { mutableStateOf(nhanVienItem.taikhoan) }
    var matKhau by remember { mutableStateOf(nhanVienItem.mk) }
    var errorMessage by remember { mutableStateOf("") }

    val authViewModel: dangnhapviewmodel = viewModel()
    val context = LocalContext.current
    val currentUser by authViewModel.currentUser.collectAsState()

    // Kiểm tra đăng nhập và quyền admin
    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate("dangnhap") {
                popUpTo("suanv") { inclusive = true }
            }
        } else if (!authViewModel.isAdmin()) {
            Toast.makeText(context, "Chỉ admin được sửa nhân viên", Toast.LENGTH_SHORT).show()
            navController.navigate("chonban") {
                popUpTo("suanv") { inclusive = true }
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
            Text(text = "Sửa Nhân Viên", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = tenNv,
                onValueChange = { tenNv = it },
                label = { Text("Tên nhân viên") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = sdt,
                onValueChange = { sdt = it },
                label = { Text("Số điện thoại") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = taiKhoan,
                onValueChange = { taiKhoan = it },
                label = { Text("Tài khoản") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = matKhau,
                onValueChange = { matKhau = it },
                label = { Text("Mật khẩu") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = {
                    if (tenNv.isBlank()) {
                        errorMessage = "Tên nhân viên không được để trống"
                    } else if (sdt.isNotBlank() && sdt.toIntOrNull() == null) {
                        errorMessage = "Số điện thoại phải là số hợp lệ"
                    } else {
                        try {
                            viewModel.sua(
                                Nhanvien(
                                    idnhanvien = id,
                                    tennv = tenNv,
                                    sdt = sdt.toIntOrNull() ?: 0,
                                    taikhoan = taiKhoan,
                                    mk = matKhau
                                )
                            )
                            navController.popBackStack()
                        } catch (e: Exception) {
                            errorMessage = "Lỗi khi sửa nhân viên: ${e.message}"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lưu")
            }

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Quay lại")
            }
        }
    }
    }

