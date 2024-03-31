package com.example.threadclone.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class MemberStatusEntity(
    @PrimaryKey
    val randomId:String= UUID.randomUUID().toString(),
    val checkInTime: String,
    val checkOutTime: String,
    @ColumnInfo("status")
    val status: String
)
