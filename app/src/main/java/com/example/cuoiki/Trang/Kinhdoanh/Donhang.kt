@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cuoiki.Trang.Kinhdoanh


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Viewmodel.*
import com.example.cuoiki.Csdl.HDTT
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun Donhang(navController: NavController, idban: Int) {
    val viewmodel: Donhangviewmodel = viewModel()
    val donhang by viewmodel.donhang.collectAsStateWithLifecycle(initialValue = emptyList())
    val viewmodel1 : HDTTviewmodel = viewModel()
    val dsdonhang = donhang.filter { it.idban == idban }

    // Tính tổng tiền
    val tongTien = dsdonhang.sumOf { it.giasp * it.soluong}
    val ngayHoadon = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Đơn hàng - Bàn $idban") },
                navigationIcon = {
                    Button(
                        onClick = { navController.navigate("Chonban") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Quay lại", color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            Button(onClick = { navController.navigate("chonmon/$idban") }) {
                Text("Thêm món")
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        viewmodel.thanhtoan(idban)
                        viewmodel1.them(HDTT(ngay = ngayHoadon, tongtien = tongTien))
                        navController.navigate("Chonban") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Thanh toán", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            // Hiển thị tổng tiền
            item {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = "Tổng tiền: $tongTien VNĐ",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
            items(dsdonhang) { donhang ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${donhang.tensp} \n Số lượng: ${donhang.soluong}\n Giá: ${donhang.giasp}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row {
                            Button(
                                onClick = { viewmodel.xoa(donhang) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("Xóa", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}