package org.d3if3038.assesment1.ui.ptest

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3038.assesment1.data.SettingDataStore
import org.d3if3038.assesment1.data.dataStore
import org.d3if3038.assesment1.db.PersonalityDao
import org.d3if3038.assesment1.db.PersonalityEntity
import org.d3if3038.assesment1.model.personality.PersonalityCategories
import org.d3if3038.assesment1.model.personality.PersonalityOption

class PTestViewModel(private val db: PersonalityDao) : ViewModel() {
    private val options = listOf(
        PersonalityOption("Forceful", "Expressive", "Restrained", "Careful"),
        PersonalityOption("Pioneering", "Exciting", "Satisfied", "Correct"),
        PersonalityOption("Bold", "Animated", "Willing", "Precise"),
//        PersonalityOption("Argumentative", "Unpredictable", "Indecisive", "Doubting"),
//        PersonalityOption("Daring", "Out-Going", "Patient", "Respectful"),
//        PersonalityOption("Self-reliant", "Persuasive", "Gentle", "Logical"),
//        PersonalityOption("Decisive", "Life-of-the-party", "Even-tempered", "Cautious"),
//        PersonalityOption("Assertive", "Popular", "Generous", "Perfectionist"),
//        PersonalityOption("Unyielding", "Colorful", "Easy-going", "Modest"),
//        PersonalityOption("Persistent", "Optimistic", "Accommodating", "Systematic"),
//        PersonalityOption("Relentless", "Talkative", "Neighborly", "Humble"),
//        PersonalityOption("Strong-Willed", "Playful", "Friendly", "Observant"),
//        PersonalityOption("Adventurous", "Charming", "Deliberate", "Disciplined"),
//        PersonalityOption("Aggressive", "Attractive", "Steady", "Restrained"),
//        PersonalityOption("Determined", "Enthusiastic", "Sympathetic", "Analytical"),
//        PersonalityOption("Commanding", "Impulsive", "Slow-paced", "Critical"),
//        PersonalityOption("Force-of-character", "Lively", "Laid-back", "Consistent"),
//        PersonalityOption("Independent", "Influential", "Kind", "Orderly"),
//        PersonalityOption("Out-spoken", "Popular", "Pleasant", "Idealistic"),
//        PersonalityOption("Impatient", "Emotional", "Procrastinator", "Serious"),
//        PersonalityOption("Competitive", "Spontaneous", "Loyal", "Thoughtful"),
//        PersonalityOption("Courageous", "Convincing", "Considerate", "Self-sacrificing"),
//        PersonalityOption("Pushy", "Flighty", "Dependent", "Stoic"),
//        PersonalityOption("Directing", "Stimulating", "Tolerant", "Conventional"),
    )

    private val firebaseDb = Firebase.firestore

    private var indexAt = MutableLiveData(0)
    private var typeDCounter = MutableLiveData(0)
    private var typeICounter = MutableLiveData(0)
    private var typeSCounter = MutableLiveData(0)
    private var typeCCounter = MutableLiveData(0)

    private fun maxVal(first: Int, second: Int, third: Int, fourth: Int): Boolean {
        return first > second && first > third && first > fourth
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

    fun getIndex(): MutableLiveData<Int> = indexAt

    fun getLengthQuestion(): Int = options.size

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

        val personality = hashMapOf(
            "fullName" to fullName,
            "age" to age,
            "isMale" to isMale,
            "personalityType" to personalityType,
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // add to firebase cloud
                firebaseDb.collection("personalities/result/${token}")
                    .add(personality)
                    .addOnSuccessListener { documentReference ->
                        Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firebase", "Error adding document", e)
                    }

                db.insert(personalityData)
            }
        }
    }

}