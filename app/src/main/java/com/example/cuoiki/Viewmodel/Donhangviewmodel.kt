package com.example.cuoiki.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuoiki.Csdl.Donhang
import com.example.cuoiki.database.DataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Donhangviewmodel(application: Application) : AndroidViewModel(application) {
    private val donhangDao = DataBase.getDatabase(application).donhangdao()
    val donhang: Flow<List<Donhang>> = donhangDao.getAllDonhang()

    fun them(donhang: Donhang) = viewModelScope.launch {
        donhangDao.themdonhang(donhang)
    }

    fun xoa(donhang: Donhang) = viewModelScope.launch {
        donhangDao.xoadonhang(donhang)
    }

    fun thanhtoan(id : Int) = viewModelScope.launch {
        donhangDao.thanhtoan(id)
    }

}