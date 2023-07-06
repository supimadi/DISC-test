package org.d3if3038.assesment1.ui.ptest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3038.assesment1.db.PersonalityDao
import org.d3if3038.assesment1.db.PersonalityEntity
import org.d3if3038.assesment1.model.personality.PersonalityCategories
import org.d3if3038.assesment1.model.personality.PersonalityOption
import org.d3if3038.assesment1.network.ApiStatus
import org.d3if3038.assesment1.network.PersonalityApi

class PTestViewModel(private val db: PersonalityDao) : ViewModel() {
    private val apiStatus = MutableLiveData<ApiStatus>()
    private val options: MutableList<PersonalityOption> = mutableListOf()

    private val firebaseDb = Firebase.firestore

    private var indexAt = MutableLiveData(0)
    private var typeDCounter = MutableLiveData(0)
    private var typeICounter = MutableLiveData(0)
    private var typeSCounter = MutableLiveData(0)
    private var typeCCounter = MutableLiveData(0)

    init {
        fetchOptions()
    }

    private fun maxVal(first: Int, second: Int, third: Int, fourth: Int): Boolean {
        return first > second && first > third && first > fourth
    }

    private fun fetchOptions() = viewModelScope.launch(Dispatchers.IO) {
        apiStatus.postValue(ApiStatus.LOADING)

        try {
            val optionsValue = async {
                PersonalityApi.service.getOptions()
            }

            options.addAll(optionsValue.await())
            apiStatus.postValue(ApiStatus.SUCCESS)
        } catch (e: Exception) {
            Log.e("FETCHING DATA", "Failure: ${e.message}")
            apiStatus.postValue(ApiStatus.FAILED)
        }
    }

    fun getQuestionLength() : Int {
        return options.size
    }

    fun getResult(): PersonalityCategories {
        val typeD = typeDCounter.value!!.toInt()
        val typeI = typeICounter.value!!.toInt()
        val typeS = typeSCounter.value!!.toInt()
        val typeC = typeCCounter.value!!.toInt()

        return if (maxVal(typeD, typeI, typeS, typeC)) {
            PersonalityCategories.TYPE_D
        } else if (maxVal(typeI, typeD, typeS, typeC)) {
            PersonalityCategories.TYPE_I
        } else if (maxVal(typeS, typeD, typeI, typeC)) {
            PersonalityCategories.TYPE_S
        } else {
            PersonalityCategories.TYPE_C
        }
    }

    fun getOptions() : PersonalityOption {
        return options[indexAt.value?.toInt()!!]
    }

    fun getApiStatus(): LiveData<ApiStatus> = apiStatus

    fun getIndex(): MutableLiveData<Int> = indexAt

    fun increaseIndex() {
        indexAt.value = if (indexAt.value == options.size) indexAt.value else indexAt.value?.plus(1)
    }
    fun increaseDPoint() {
        typeDCounter.value = typeDCounter.value?.plus(1)
    }
    fun increaseIPoint() {
        typeICounter.value = typeICounter.value?.plus(1)
    }
    fun increaseSPoint() {
        typeSCounter.value = typeSCounter.value?.plus(1)
    }
    fun increaseCPoint() {
         typeCCounter.value = typeCCounter.value?.plus(1)
    }

    fun persistPersonalityData(
        fullName: String,
        age: Int,
        isMale: Boolean,
        personalityType: PersonalityCategories,
        token: String
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

                if (token.isEmpty()) {
                    return@withContext
                }

                // add data to firebase cloud
                val doc = firebaseDb.collection("personalities_result_${token}").document()
                personalityData.documentId = doc.id

                doc.set(personalityData)
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error adding document", e)
                    return@addOnFailureListener
                }

            }
        }
    }

}
