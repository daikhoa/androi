package com.example.cuoiki.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuoiki.Csdl.*
import com.example.cuoiki.database.DataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Sanphamviewmodel(application: Application) : AndroidViewModel(application) {
    private val sanphamDao = DataBase.getDatabase(application).sanphamdao()
    val sanpham: Flow<List<Sanpham>> = sanphamDao.getAllSanpham()

    fun them(sanpham: Sanpham) = viewModelScope.launch {
        sanphamDao.themsanpham(sanpham)
    }

    fun sua(sanpham: Sanpham) = viewModelScope.launch {
        sanphamDao.suasanpham(sanpham)
    }

    fun xoa(sanpham: Sanpham) = viewModelScope.launch {
        sanphamDao.xoasanpham(sanpham)
    }

}