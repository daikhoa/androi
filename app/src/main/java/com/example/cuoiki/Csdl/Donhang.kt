package com.example.cuoiki.Csdl

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "donhang",
        foreignKeys = [
        ForeignKey(
        entity = Ban::class,
        parentColumns = ["idban"],
        childColumns = ["idban"],
        onDelete = ForeignKey.CASCADE // Hành vi xóa cascade
    )
])

data class Donhang(
    @PrimaryKey(autoGenerate = true) val iddonhang: Int = 0,
    val tensp: String,
    val giasp: Double,
    val soluong: Int,
    val idban: Int
)


@Dao
interface DonhangDao {
    @Query("SELECT * FROM donhang ORDER BY iddonhang ASC")
    fun getAllDonhang(): Flow<List<Donhang>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun themdonhang(donhang: Donhang)

    @Delete
    suspend fun xoadonhang(donhang: Donhang)

    @Query("DELETE FROM donhang WHERE idban = :idban")
    suspend fun thanhtoan(idban: Int)
}