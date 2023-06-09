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
        PersonalityOption("Dominan", "Ekspresif", "Terkendali", "Peduli"),
        PersonalityOption("Terencana", "Menyenangkan", "Memuaskan", "Jujur"),
        PersonalityOption("Tegas", "Bersemangat", "Rela", "Teliti"),
        PersonalityOption("Berargumen", "Tidak terprediksi", "Ragu", "Tidak percaya diri"),
        PersonalityOption("Berani mengambil resiko", "Terbuka", "Sabar", "Hormat"),
        PersonalityOption("Bisa diandalkan", "Meyakinkan", "Lemah lembut", "Logika"),
        PersonalityOption("Menentukan", "Pemeriah suasana", "Tenang", "Berhati - hati"),
        PersonalityOption("Asertif", "Terkenal", "Berpengaruh", "Sempurna"),
        PersonalityOption("Keras kepala", "Bersemangat", "Santai", "Tertutup"),
        PersonalityOption("Gigih", "Optimis", "Rela berkorban", "Sistematis"),
        PersonalityOption("Memukau", "Suka berbicara", "Ramah", "Rendah hati"),
        PersonalityOption("Susah diatur", "Santai", "Murah hati", "Realistis"),
        PersonalityOption("Suka tantangan", "Menawan", "Konsisten", "Disiplin"),
        PersonalityOption("Agresif", "Atraktif", "Stabil", "Terkendali"),
        PersonalityOption("Bertekad", "Antusias", "Simpatis", "Analitis"),
        PersonalityOption("Mengarahkan", "Impulsif", "Lambat", "Kritis"),
        PersonalityOption("Karakter kuat", "Penuh semangat", "Santai", "Konsisten"),
        PersonalityOption("Mandiri", "Suka bersosialisasi", "Baik", "Terstruktur"),
        PersonalityOption("Berterus terang", "Terkenal", "Mudah bergaul", "Idealis"),
        PersonalityOption("Tidak sabaran", "Emosional", "Sering menunda pekerjaan", "Serius"),
        PersonalityOption("Kompetitif", "Spontan", "Setia", "Tanggap"),
        PersonalityOption("Berani", "Mempesona", "Menghargai", "Rela berkorban"),
        PersonalityOption("Ambisius", "Banyak Tingkah", "Bergantung", "Tabah"),
        PersonalityOption("Mengarahkan", "Memotivasi", "Toleransi", "Mengikuti kebiasaan"),
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


        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // add data to firebase cloud

                if (token.isEmpty()) {
                    return@withContext
                }

                val doc = firebaseDb.collection("personalities_result_${token}").document()
                personalityData.documentId = doc.id

                doc.set(personalityData)
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error adding document", e)
                    return@addOnFailureListener
                }

                db.insert(personalityData)

            }
        }
    }

}