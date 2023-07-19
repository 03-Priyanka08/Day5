package com.example.day5

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Demo_Table")
data class DetailEntity(
    @PrimaryKey(autoGenerate = true)
    var uID: Int = 0,

    @ColumnInfo("Phone_No")
    var pNo: String,

    @ColumnInfo("Password")
    var pwd: String
)