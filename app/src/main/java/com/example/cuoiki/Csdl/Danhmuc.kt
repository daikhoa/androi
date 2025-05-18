package com.example.cuoiki.Csdl
import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "danhmuc")
data class Danhmuc(
    @PrimaryKey(autoGenerate = true) val iddanhmuc: Int = 0,
    val tendanhmuc: String
)


@Dao
interface DanhmucDao {
    @Query("SELECT * FROM danhmuc ORDER BY iddanhmuc ASC")
    fun getAllDanhmuc(): Flow<List<Danhmuc>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun themdanhmuc(danhmuc: Danhmuc)

    @Update
    suspend fun suadanhmuc(danhmuc: Danhmuc)

    @Delete
    suspend fun xoadanhmuc(danhmuc: Danhmuc)
}
