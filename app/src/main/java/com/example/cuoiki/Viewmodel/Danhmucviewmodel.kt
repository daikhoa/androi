package com.example.cuoiki.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuoiki.Csdl.Danhmuc
import com.example.cuoiki.database.DataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Danhmucviewmodel(application: Application) : AndroidViewModel(application) {
    private val danhmucDao =  DataBase.getDatabase(application).danhmucdao()
    val danhmuc: Flow<List<Danhmuc>> = danhmucDao.getAllDanhmuc()

    fun them(danhmuc: Danhmuc) = viewModelScope.launch {
        danhmucDao.themdanhmuc(danhmuc)
    }

    fun sua(danhmuc: Danhmuc) = viewModelScope.launch {
        danhmucDao.suadanhmuc(danhmuc)
    }

    fun xoa(danhmuc: Danhmuc) = viewModelScope.launch {
        danhmucDao.xoadanhmuc(danhmuc)
    }

}