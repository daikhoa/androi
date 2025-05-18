package com.example.cuoiki.database

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cuoiki.Csdl.*


@Database(entities = [Sanpham::class,Ban::class,Danhmuc::class,Donhang::class,HDTT::class,Nhanvien::class], version = 2, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun banDao(): BanDao
    abstract fun danhmucdao(): DanhmucDao
    abstract fun donhangdao(): DonhangDao
    abstract fun hdttdao(): HDTTDao
    abstract fun nhanviendao(): NhanvienDao
    abstract fun sanphamdao(): SanphamDao


    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "database"
                ).fallbackToDestructiveMigration()  // Tự động xóa và tạo lại DB nếu schema thay đổi mà chưa có migration
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}