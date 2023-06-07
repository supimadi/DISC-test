package org.d3if3038.assesment1.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.d3if3038.assesment1.model.personality.PersonalityCategories

@Entity(tableName = "personalities")
data class PersonalityEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var documentId: String = "",
    var fullName: String,
    var age: Int,
    var isMale: Boolean,
    var personalityType: PersonalityCategories,
    var date: Long = System.currentTimeMillis()
)