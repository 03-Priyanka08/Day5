package com.example.day5

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DetailDAO {
    @Insert
    suspend fun addData(item: DetailEntity)

    @Query("SELECT * FROM Demo_Table WHERE Phone_No= :x")
    suspend fun getPhnNo(x: String): DetailEntity

    @Update
    suspend fun updatePass(item: DetailEntity)

    @Delete
    suspend fun delUser(item: DetailEntity)

    @Query("DELETE FROM Demo_Table")
    suspend fun delAll()
}