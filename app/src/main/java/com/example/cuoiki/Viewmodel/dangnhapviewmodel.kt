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
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()
    private val prefs = application.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    init {
        val userId = prefs.getInt("user_id", -1)
        println("dangnhapviewmodel init: userId = $userId")
        if (userId != -1) {
            viewModelScope.launch {
                val user = nhanvienDao.getNhanvienById(userId)
                println("dangnhapviewmodel init: Fetched user = $user")
                if (user != null) {
                    _currentUser.value = user
                } else {
                    prefs.edit { remove("user_id").apply() }
                    _errorMessage.value = "Phiên đăng nhập không hợp lệ, vui lòng đăng nhập lại"
                }
            }
        }
    }

    fun login(taikhoan: String, matkhau: String) {
        viewModelScope.launch {
            println("Login attempt: taikhoan = $taikhoan")
            try {
                val user = nhanvienDao.login(taikhoan, matkhau)
                println("Login result: user = $user")
                if (user != null) {
                    _currentUser.value = user
                    prefs.edit { putInt("user_id", user.idnhanvien).apply() }
                    _errorMessage.value = ""
                } else {
                    _errorMessage.value = "Tài khoản hoặc mật khẩu không đúng"
                }
            } catch (e: Exception) {
                println("Login error: ${e.message}")
                _errorMessage.value = "Đã có lỗi xảy ra: ${e.message}"
            }
        }
    }

    fun logout() {
        println("Logout called")
        _currentUser.value = null
        prefs.edit { remove("user_id").apply() }
        _errorMessage.value = ""
    }

    fun isAdmin(): Boolean {
        val isAdmin = _currentUser.value?.taikhoan == "admin"
        println("isAdmin checked: result = $isAdmin")
        return isAdmin
    }
}