package com.example.cuoiki.Trang.Doanhthu

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cuoiki.Viewmodel.HDTTviewmodel
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun Doanhthu(navController: NavController) {
    // Khởi tạo ViewModel
    val viewModel: HDTTviewmodel = viewModel()
    val context = LocalContext.current

    // State để lưu ngày bắt đầu và kết thúc
    var startDate by remember { mutableStateOf(TextFieldValue("")) }
    var endDate by remember { mutableStateOf(TextFieldValue("")) }

    // Lấy dữ liệu doanh thu từ ViewModel
    val revenue by viewModel.revenue.collectAsState()

    // Format ngày và kiểm tra định dạng
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
        isLenient = false // Không cho phép ngày không hợp lệ
    }

    Scaffold(
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Tính Doanh Thu",
                style = MaterialTheme.typography.headlineMedium
            )

            // TextField nhập ngày bắt đầu
            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Ngày bắt đầu (dd/MM/yyyy)") },
                isError = startDate.text.isNotEmpty() && !isValidDate(startDate.text, dateFormatter),
                modifier = Modifier.fillMaxWidth()
            )

            // TextField nhập ngày kết thúc
            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("Ngày kết thúc (dd/MM/yyyy)") },
                isError = endDate.text.isNotEmpty() && !isValidDate(endDate.text, dateFormatter),
                modifier = Modifier.fillMaxWidth()
            )

            // Nút tính doanh thu
            Button(
                onClick = {
                    if (startDate.text.isNotEmpty() && endDate.text.isNotEmpty()) {
                        // Kiểm tra định dạng ngày
                        if (isValidDate(startDate.text, dateFormatter) && isValidDate(endDate.text, dateFormatter)) {
                            // Kiểm tra ngày kết thúc không trước ngày bắt đầu
                            val start = dateFormatter.parse(startDate.text)
                            val end = dateFormatter.parse(endDate.text)
                            if (start != null && end != null && end >= start) {
                                viewModel.tinhdoanhthu(startDate.text, endDate.text)
                            } else {
                                Toast.makeText(context, "Ngày kết thúc phải sau ngày bắt đầu", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Vui lòng nhập ngày đúng định dạng dd/MM/yyyy", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Vui lòng nhập cả ngày bắt đầu và kết thúc", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = startDate.text.isNotEmpty() && endDate.text.isNotEmpty()
            ) {
                Text(text = "Tính Doanh Thu")}


                // Hiển thị kết quả doanh thu
                Text(
                    text = "Doanh thu: ${revenue?.formatAsCurrency() ?: "Chưa có dữ liệu"}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }


}

// Hàm mở rộng để format số thành tiền tệ
fun Double.formatAsCurrency(): String {
    return String.format("%,.0f VNĐ", this)
}

// Hàm kiểm tra định dạng ngày
fun isValidDate(date: String, formatter: SimpleDateFormat): Boolean {
    return try {
        formatter.parse(date)
        true
    } catch (e: Exception) {
        false
    }
}