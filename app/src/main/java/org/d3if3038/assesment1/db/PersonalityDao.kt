package org.d3if3038.assesment1.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonalityDao {

    @Insert
    fun insert(vararg personality: PersonalityEntity)

    @Query("SELECT * FROM personalities ORDER BY id DESC")
    fun getNewestPersonalities() : LiveData<MutableList<PersonalityEntity>>

    @Query("SELECT * FROM personalities WHERE id = :personalityId")
    fun getPersonalityById(personalityId: Int): LiveData<PersonalityEntity>

    @Query("DELETE FROM personalities")
    fun clearPersonalitiesData()

    @Query("DELETE FROM personalities WHERE id = :personalityId")
    fun deletePersonality(personalityId: Int)
}