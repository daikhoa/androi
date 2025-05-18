package com.example.cuoiki.Csdl

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "ban")
data class Ban(
    @PrimaryKey(autoGenerate = true) val idban: Int = 0,
    val soban: Int
)

@Dao
interface BanDao {
    @Query("SELECT * FROM ban ORDER BY soban ASC")
    fun getAllBan(): Flow<List<Ban>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun themban(ban: Ban)

    @Update
    suspend fun suaban(ban: Ban)


    @Delete
    suspend fun xoaban(ban: Ban)
}

