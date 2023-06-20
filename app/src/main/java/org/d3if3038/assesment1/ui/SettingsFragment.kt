package org.d3if3038.assesment1.ui

import android.os.Bundle
import android.widget.Toast
import androidx.preference.EditTextPreference
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

//        val tokenPreference: EditTextPreference? = findPreference(
//            getString(R.string.token_admin_preferences)
//        )
//
//        tokenPreference!!.setOnPreferenceClickListener {
//            Toast.makeText(requireContext(), getString(R.string.feature_finding), Toast.LENGTH_LONG).show()
//            true
//        }

    }
}