package com.example.cuoiki.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuoiki.Csdl.Ban
import com.example.cuoiki.Csdl.Nhanvien
import com.example.cuoiki.database.DataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Banviewmodel(application: Application) : AndroidViewModel(application) {
    private val banDao = DataBase.getDatabase(application).banDao()
    val ban: Flow<List<Ban>> = banDao.getAllBan()

    fun them(ban: Ban) = viewModelScope.launch {
        banDao.themban(ban)
    }

    fun sua(ban: Ban) = viewModelScope.launch {
        banDao.suaban(ban)
    }

    fun xoa(ban: Ban) = viewModelScope.launch {
        banDao.xoaban(ban)
    }

}