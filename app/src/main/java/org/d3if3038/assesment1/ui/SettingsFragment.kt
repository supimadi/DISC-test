package org.d3if3038.assesment1.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.transition.TransitionInflater
import org.d3if3038.assesment1.R
import org.d3if3038.assesment1.data.SettingDataStore
import org.d3if3038.assesment1.data.dataStore

class SettingsFragment : PreferenceFragmentCompat() {

    private val settingDataStore: SettingDataStore by lazy {
        SettingDataStore(requireContext().dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transitionInflater = TransitionInflater.from(requireContext())
        enterTransition = transitionInflater.inflateTransition(R.transition.slide_right)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = settingDataStore
        setPreferencesFromResource(R.xml.prefrences, rootKey)
    }
}