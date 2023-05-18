package org.d3if3038.assesment1.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3038.assesment1.db.PersonalityDao
import org.d3if3038.assesment1.db.PersonalityEntity
import org.d3if3038.assesment1.model.personality.PersonalityCategories

class ResultViewModel (private val db: PersonalityDao) : ViewModel() {

    fun persistPersonalityData(
        fullName: String,
        age: Int,
        isMale: Boolean,
        personalityType: PersonalityCategories
    ) {

        val personalityData = PersonalityEntity(
            fullName = fullName,
            age = age,
            isMale = isMale,
            personalityType = personalityType
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(personalityData)
            }
        }
    }

}