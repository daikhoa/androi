package com.example.cuoiki.Csdl

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "hdtt")
data class HDTT(
    @PrimaryKey(autoGenerate = true) val idhoadon: Int = 0,
    val ngay: String,
    val tongtien: Double
)


@Dao
interface HDTTDao {
    @Query("SELECT * FROM hdtt ORDER BY idhoadon ASC")
    fun getAllHDTT(): Flow<List<HDTT>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun thanhtoan(hdtt: HDTT)
}


