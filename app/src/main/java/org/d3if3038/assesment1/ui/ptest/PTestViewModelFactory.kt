package org.d3if3038.assesment1.ui.ptest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3038.assesment1.db.PersonalityDao

class PTestViewModelFactory(private val db : PersonalityDao): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PTestViewModel::class.java)) {
            return PTestViewModel(db) as T
        }

        throw IllegalArgumentException("ViewModel Class Does Not the Same With ViewModelFactory")
    }
}