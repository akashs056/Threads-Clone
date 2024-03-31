package com.example.threadclone.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.threadclone.Model.HourlyCheckInCount
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT SUBSTR(checkInTime, 1, 2) AS checkInHour, COUNT(*) AS checkInCount FROM MemberStatusEntity GROUP BY checkInHour ORDER BY checkInHour")
    fun getHourlyCheckInCounts(): List<HourlyCheckInCount>

    @Insert
    suspend fun insertMemberStatus(memberStatus: MemberStatusEntity)

}