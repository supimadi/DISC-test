package org.d3if3038.assesment1.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3038.assesment1.model.personality.Profile

class WelcomeViewModel : ViewModel() {

    private val personalityTest = MutableLiveData<Profile?>()


    fun startPersonalityTest(profile: Profile) {
        personalityTest.value = profile
    }

    fun doneNavPersonalityTest() {
        personalityTest.value = null
    }

    fun getNavPersonalityTest(): LiveData<Profile?> = personalityTest
}