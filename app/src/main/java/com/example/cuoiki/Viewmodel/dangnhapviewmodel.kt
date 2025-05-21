package com.example.cuoiki.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuoiki.Csdl.Nhanvien
import com.example.cuoiki.database.DataBase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.content.Context
import androidx.core.content.edit

class dangnhapviewmodel(application: Application) : AndroidViewModel(application) {
    private val nhanvienDao = DataBase.getDatabase(application).nhanviendao()
    private val _currentUser = MutableStateFlow<Nhanvien?>(null)
    val currentUser: StateFlow<Nhanvien?> = _currentUser.asStateFlow()
    private val prefs = application.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    init {
        // Kiểm tra trạng thái đăng nhập khi khởi tạo
        val userId = prefs.getInt("user_id", -1)
        if (userId != -1) {
            viewModelScope.launch {
                _currentUser.value = nhanvienDao.getNhanvienById(userId)
            }
        }
    }

    fun login(taikhoan: String, matkhau: String) {
        viewModelScope.launch {
            val user = nhanvienDao.login(taikhoan, matkhau)
            _currentUser.value = user
            if (user != null) {
                prefs.edit { putInt("user_id", user.idnhanvien).apply() }
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        prefs.edit { remove("user_id").apply() }
    }

    fun isAdmin(): Boolean {
        return _currentUser.value?.taikhoan == "admin"
    }
}