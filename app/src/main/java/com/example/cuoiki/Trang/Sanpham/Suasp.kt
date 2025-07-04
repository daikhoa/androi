package com.example.cuoiki.Trang.Sanpham

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cuoiki.Csdl.Sanpham
import com.example.cuoiki.Viewmodel.Sanphamviewmodel
import com.example.cuoiki.Viewmodel.Danhmucviewmodel
import com.example.cuoiki.Csdl.Danhmuc
import com.example.cuoiki.Viewmodel.dangnhapviewmodel
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@Composable
fun Suasp(navController: NavController, idsanpham: Int) {
    val viewmodel: Sanphamviewmodel = viewModel()
    val viewmodel1: Danhmucviewmodel = viewModel()
    val danhmuc by viewmodel1.danhmuc.collectAsStateWithLifecycle(initialValue = emptyList())
    // Lấy sản phẩm theo ID
    val sanPham by viewmodel.sanpham.collectAsStateWithLifecycle(initialValue = emptyList() )
    val sanpham= sanPham.find { it.idsanpham == idsanpham }
    var tenmon by remember { mutableStateOf("") }
    var giasp by remember { mutableStateOf("") }
    var hinhanh by remember { mutableStateOf<Uri?>(null) }
    var selectedDanhMuc by remember { mutableStateOf<Danhmuc?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authViewModel: dangnhapviewmodel = viewModel()
    val currentUser by authViewModel.currentUser.collectAsState()

    // Kiểm tra đăng nhập và quyền admin
    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate("dangnhap") {
                popUpTo("suasp") { inclusive = true }
            }
        } else if (!authViewModel.isAdmin()) {
            Toast.makeText(context, "Chỉ admin được sửa sản phẩm", Toast.LENGTH_SHORT).show()
            navController.navigate("chonban") {
                popUpTo("suasp") { inclusive = true }
            }
        }
    }

    // Chỉ hiển thị nếu là admin
    if (currentUser != null && authViewModel.isAdmin()) {
        // Khởi tạo dữ liệu từ sản phẩm
        LaunchedEffect(sanpham) {
            sanpham?.let { sp ->
                tenmon = sp.tensp
                giasp = sp.giasp.toString()
                hinhanh = if (sp.hinhanh.isNotEmpty()) Uri.parse(sp.hinhanh) else null
                selectedDanhMuc = danhmuc.find { it.iddanhmuc == sp.iddanhmuc }
            }
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            hinhanh = uri
        }

        // Kiểm tra danh mục
        LaunchedEffect(danhmuc) {
            println("Danh mục: $danhmuc")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Sửa Sản Phẩm", style = MaterialTheme.typography.titleLarge)
            Text(text = "Số danh mục: ${danhmuc.size}") // Kiểm tra số lượng danh mục

            OutlinedTextField(
                value = tenmon,
                onValueChange = { tenmon = it },
                label = { Text("Tên món ăn") },
                modifier = Modifier.fillMaxWidth(),
                isError = tenmon.isBlank() && errorMessage.isNotEmpty()
            )

            OutlinedTextField(
                value = giasp,
                onValueChange = { if (it.isEmpty() || it.toDoubleOrNull() != null) giasp = it },
                label = { Text("Giá sản phẩm") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                isError = (giasp.isBlank() || giasp.toDoubleOrNull() == null) && errorMessage.isNotEmpty()
            )

            Box {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                ) {
                    OutlinedTextField(
                        value = selectedDanhMuc?.tendanhmuc ?: "Chọn danh mục",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Danh mục") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = selectedDanhMuc == null && errorMessage.isNotEmpty()
                    )
                }

                DropdownMenu(
                    expanded = expanded && danhmuc.isNotEmpty(),
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                        .zIndex(1f)
                ) {
                    if (danhmuc.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("Không có danh mục") },
                            onClick = { expanded = false }
                        )
                    } else {
                        danhmuc.forEach { danhMuc ->
                            DropdownMenuItem(
                                text = { Text(danhMuc.tendanhmuc) },
                                onClick = {
                                    selectedDanhMuc = danhMuc
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            if (hinhanh != null) {
                Image(
                    painter = rememberAsyncImagePainter(hinhanh),
                    contentDescription = "Hình ảnh sản phẩm",
                    modifier = Modifier
                        .size(150.dp)
                        .clickable { launcher.launch("image/*") }
                )
            } else {
                OutlinedButton(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Chọn hình ảnh")
                }
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    if (tenmon.isBlank()) {
                        errorMessage = "Vui lòng nhập tên món ăn"
                    } else if (giasp.isBlank() || giasp.toDoubleOrNull() == null) {
                        errorMessage = "Vui lòng nhập giá hợp lệ"
                    } else if (selectedDanhMuc == null) {
                        errorMessage = "Vui lòng chọn danh mục"
                    } else {
                        val imagePath = hinhanh?.let { saveImageToInternalStorage(context, it) } ?: sanpham?.hinhanh
                        if (imagePath != null) {
                            val updatedSanpham = Sanpham(
                                idsanpham = idsanpham,
                                tensp = tenmon,
                                giasp = giasp.toDouble(),
                                iddanhmuc = selectedDanhMuc!!.iddanhmuc,
                                hinhanh = imagePath
                            )
                            viewmodel.sua(updatedSanpham)
                            navController.popBackStack()
                        } else {
                            errorMessage = "Lỗi khi lưu hình ảnh"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cập nhật sản phẩm")
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

private fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = "${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}