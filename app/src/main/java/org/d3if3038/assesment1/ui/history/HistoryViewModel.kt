package org.d3if3038.assesment1.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3038.assesment1.db.PersonalityDao

class HistoryViewModel(private val db: PersonalityDao) : ViewModel() {
    val personalityData = db.getNewestPersonalities()

    fun deleteAllPersonalityData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearPersonalitiesData()
        }
    }
    fun deletePersonalityData(index: Int) = viewModelScope.launch {
        if (personalityData.value == null) return@launch

        val id = personalityData.value!![index].id.toInt()
        withContext(Dispatchers.IO) {
            db.deletePersonality(id)
        }

    }
}