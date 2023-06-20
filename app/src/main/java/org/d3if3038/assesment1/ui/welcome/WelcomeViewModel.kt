package org.d3if3038.assesment1.ui.welcome

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3038.assesment1.model.personality.Profile
import org.d3if3038.assesment1.network.FunFactsWorker
import org.d3if3038.assesment1.network.PersonalityApi
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class WelcomeViewModel : ViewModel() {

    private val personalityTest = MutableLiveData<Profile?>()


    fun startPersonalityTest(profile: Profile) {
        personalityTest.value = profile
    }

    fun doneNavPersonalityTest() {
        personalityTest.value = null
    }

    fun fireFactNotif(app: Application) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val funFacts = PersonalityApi.service.getFunFacts()
            val workerRequest = PeriodicWorkRequestBuilder<FunFactsWorker>(
                1, TimeUnit.DAYS,
                10, TimeUnit.HOURS
                )
                .setInputData(workDataOf(
                    "FUN_FACTS" to funFacts[Random.nextInt(0, funFacts.size)].facts
                ))
                .build()

//            WorkManager.getInstance(app).enqueueUniqueWork(
//                FunFactsWorker.WORK_NAME,
//                ExistingWorkPolicy.REPLACE,
//                workerRequest
//            )

            WorkManager.getInstance(app).enqueue(workerRequest)

        } catch (e: Exception) {
            Log.e("NOTIFICATION", "Failure: ${e.message}")
        }
    }

    fun getNavPersonalityTest(): LiveData<Profile?> = personalityTest
}