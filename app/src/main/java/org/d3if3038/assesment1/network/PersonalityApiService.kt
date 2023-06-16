package org.d3if3038.assesment1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3038.assesment1.model.facts.FunFacts
import org.d3if3038.assesment1.model.personality.PersonalityOption
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://raw.githubusercontent.com/supimadi/DISC-test/static-api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PersonalityApiService {

    @GET("data-options.json")
    suspend fun getOptions(): List<PersonalityOption>

    @GET("fun-facts.json")
    suspend fun getFunFacts(): List<FunFacts>
}

enum class ApiStatus { LOADING, FAILED, SUCCESS }

object PersonalityApi {
    val service: PersonalityApiService by lazy {
        retrofit.create(PersonalityApiService::class.java)
    }

    fun getImagePersonalityTypeUrl(personalityType: String): String {
        return "${BASE_URL}personality_${personalityType}.png"
    }

}