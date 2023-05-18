package org.d3if3038.assesment1.ui.history

import androidx.lifecycle.ViewModel
import org.d3if3038.assesment1.db.PersonalityDao

class HistoryViewModel(private val db: PersonalityDao) : ViewModel() {
    val personalityData = db.getNewestPersonalities()
}