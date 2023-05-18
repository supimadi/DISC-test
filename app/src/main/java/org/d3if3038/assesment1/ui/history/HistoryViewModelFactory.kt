package org.d3if3038.assesment1.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3038.assesment1.db.PersonalityDao

class HistoryViewModelFactory (private val db : PersonalityDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(db) as T
        }

        throw IllegalArgumentException("ViewModel Class Does Not the Same With ViewModelFactory")
    }
}