package com.example.cuoiki.Csdl
import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "sanpham",
    foreignKeys = [
        ForeignKey(
            entity = Danhmuc::class,
            parentColumns = ["iddanhmuc"],
            childColumns = ["iddanhmuc"],
            onDelete = ForeignKey.CASCADE // Hành vi xóa cascade
        )
    ])
data class Sanpham(
    @PrimaryKey(autoGenerate = true) val idsanpham: Int = 0,
    val tensp: String,
    val giasp: Double,
    val hinhanh: String,
    val iddanhmuc:Int

)


@Dao
interface SanphamDao {


    @Query("SELECT * FROM sanpham ORDER BY tensp ASC")
    fun getAllSanpham(): Flow<List<Sanpham>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun themsanpham(sanpham: Sanpham)

    @Update
    suspend fun suasanpham(sanpham: Sanpham)

    @Delete
    suspend fun xoasanpham(sanpham: Sanpham)


}