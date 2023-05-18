package org.d3if3038.assesment1.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3038.assesment1.db.PersonalityDao

class ResultViewModelFactory (private val db: PersonalityDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(db) as T
        }

        throw IllegalArgumentException("ViewModel Class Does Not the Same With ViewModelFactory")
    }
}