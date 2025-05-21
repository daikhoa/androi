package com.example.cuoiki.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuoiki.Csdl.HDTT
import com.example.cuoiki.Csdl.Nhanvien
import com.example.cuoiki.database.DataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HDTTviewmodel(application: Application) : AndroidViewModel(application) {
    private val hdDao = DataBase.getDatabase(application).hdttdao()
    val hoadon: Flow<List<HDTT>> = hdDao.getAllHDTT()
    private val doanhthu = MutableStateFlow<Double?>(null)
    val revenue: StateFlow<Double?> = doanhthu.asStateFlow()

    fun them(hdtt: HDTT) = viewModelScope.launch {
        hdDao.thanhtoan(hdtt)
    }

    fun tinhdoanhthu(startDate: String, endDate: String) {
        viewModelScope.launch {
            val result = hdDao.doanhthu(startDate, endDate)
            doanhthu.value = result
        }
    }




}