package com.example.cuoiki.Csdl

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "nhanvien")
data class Nhanvien(
    @PrimaryKey(autoGenerate = true) val idnhanvien: Int = 0,
    val tennv: String,
    val sdt: Int,
    val taikhoan: String,
    val mk: String
)

@Dao
interface NhanvienDao {
    @Query("SELECT * FROM nhanvien ORDER BY idnhanvien ASC")
    fun getAllNhanvien(): Flow<List<Nhanvien>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun themnhanvien(nhanvien: Nhanvien)

    @Update
    suspend fun suanhanvien(nhanvien: Nhanvien)

    @Delete
    suspend fun xoanhanvien(nhanvien: Nhanvien)

    @Query("SELECT * FROM nhanvien WHERE taikhoan = :taikhoan AND mk = :matkhau")
    suspend fun login(taikhoan: String, matkhau: String): Nhanvien?

    @Query("SELECT * FROM nhanvien WHERE idnhanvien = :id")
    suspend fun getNhanvienById(id: Int): Nhanvien?
}