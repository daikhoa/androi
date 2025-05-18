package com.example.cuoiki.Trang.Sanpham

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cuoiki.Csdl.Danhmuc
import com.example.cuoiki.Csdl.Sanpham
import com.example.cuoiki.Viewmodel.Danhmucviewmodel
import com.example.cuoiki.Viewmodel.Sanphamviewmodel
import java.io.File

@Composable
fun Danhsachsp(navController: NavController) {
    val sanphamViewModel: Sanphamviewmodel = viewModel()
    val danhmucViewModel: Danhmucviewmodel = viewModel()
    val danhSachSanPham by sanphamViewModel.sanpham.collectAsStateWithLifecycle(initialValue = emptyList())
    val danhSachDanhMuc by danhmucViewModel.danhmuc.collectAsStateWithLifecycle(initialValue = emptyList())
    var selectedDanhMuc by remember { mutableStateOf<Danhmuc?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Lọc danh sách sản phẩm dựa trên danh mục được chọn
    val filteredSanPham = if (selectedDanhMuc == null) {
        danhSachSanPham
    } else {
        danhSachSanPham.filter { it.iddanhmuc == selectedDanhMuc!!.iddanhmuc }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("Themsp") },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Thêm", style = MaterialTheme.typography.titleLarge)
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Dropdown để chọn danh mục
            Box {
                OutlinedTextField(
                    value = selectedDanhMuc?.tendanhmuc ?: "Tất cả danh mục",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Chọn danh mục") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Thêm tùy chọn "Tất cả danh mục"
                    DropdownMenuItem(
                        text = { Text("Tất cả danh mục") },
                        onClick = {
                            selectedDanhMuc = null
                            expanded = false
                        }
                    )
                    // Các danh mục từ cơ sở dữ liệu
                    danhSachDanhMuc.forEach { danhMuc ->
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

            // Danh sách sản phẩm
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredSanPham) { sp ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(File(sp.hinhanh)),
                                contentDescription = "Hình ảnh sản phẩm",
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(4.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = sp.tensp, style = MaterialTheme.typography.titleMedium)
                                Text(text = "Giá: ${sp.giasp}", style = MaterialTheme.typography.bodyMedium)
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Button(
                                    onClick = {
                                        navController.navigate("suasp/${sp.idsanpham}")
                                    },
                                    modifier = Modifier.padding(2.dp)
                                ) {
                                    Text("Sửa")
                                }

                                OutlinedButton(
                                    onClick = {
                                        sanphamViewModel.xoa(sp)
                                    },
                                    modifier = Modifier.padding(2.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colorScheme.error
                                    )
                                ) {
                                    Text("Xóa")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}