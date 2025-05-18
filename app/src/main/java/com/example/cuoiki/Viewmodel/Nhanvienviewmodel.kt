package com.example.cuoiki.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuoiki.Csdl.Nhanvien
import com.example.cuoiki.Csdl.Sanpham
import com.example.cuoiki.database.DataBase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class Nhanvienviewmodel(application: Application) : AndroidViewModel(application) {
    private val nhanvienDao = DataBase.getDatabase(application).nhanviendao()
    val nhanvien: Flow<List<Nhanvien>> = nhanvienDao.getAllNhanvien()

    fun them(nhanvien: Nhanvien) = viewModelScope.launch {
        nhanvienDao.themnhanvien(nhanvien)
    }

    fun sua(nhanvien: Nhanvien) = viewModelScope.launch {
        nhanvienDao.suanhanvien(nhanvien)
    }

    fun xoa(nhanvien: Nhanvien) = viewModelScope.launch {
        nhanvienDao.xoanhanvien(nhanvien)
    }

}