package org.d3if3038.assesment1.ui.ptest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3038.assesment1.model.personality.PersonalityOption

class PTestViewModel : ViewModel() {
    private val options = listOf(
        PersonalityOption("Forceful", "Expressive", "Restrained", "Careful"),
        PersonalityOption("Pioneering", "Exciting", "Satisfied", "Correct"),
        PersonalityOption("Bold", "Animated", "Willing", "Precise"),
        PersonalityOption("Argumentative", "Unpredictable", "Indecisive", "Doubting"),
        PersonalityOption("Daring", "Out-Going", "Patient", "Respectful"),
        PersonalityOption("Self-reliant", "Persuasive", "Gentle", "Logical"),
        PersonalityOption("Decisive", "Life-of-the-party", "Even-tempered", "Cautious"),
        PersonalityOption("Assertive", "Popular", "Generous", "Perfectionist"),
        PersonalityOption("Unyielding", "Colorful", "Easy-going", "Modest"),
        PersonalityOption("Persistent", "Optimistic", "Accommodating", "Systematic"),
        PersonalityOption("Relentless", "Talkative", "Neighborly", "Humble"),
        PersonalityOption("Strong-Willed", "Playful", "Friendly", "Observant"),
        PersonalityOption("Adventurous", "Charming", "Deliberate", "Disciplined"),
        PersonalityOption("Aggressive", "Attractive", "Steady", "Restrained"),
        PersonalityOption("Determined", "Enthusiastic", "Sympathetic", "Analytical"),
        PersonalityOption("Commanding", "Impulsive", "Slow-paced", "Critical"),
        PersonalityOption("Force-of-character", "Lively", "Laid-back", "Consistent"),
        PersonalityOption("Independent", "Influential", "Kind", "Orderly"),
        PersonalityOption("Out-spoken", "Popular", "Pleasant", "Idealistic"),
        PersonalityOption("Impatient", "Emotional", "Procrastinator", "Serious"),
        PersonalityOption("Competitive", "Spontaneous", "Loyal", "Thoughtful"),
        PersonalityOption("Courageous", "Convincing", "Considerate", "Self-sacrificing"),
        PersonalityOption("Pushy", "Flighty", "Dependent", "Stoic"),
        PersonalityOption("Directing", "Stimulating", "Tolerant", "Conventional"),
    )

    private var indexAt = MutableLiveData<Int>(0)
    private var typeDCounter = MutableLiveData<Int>()
    private var typeICounter = MutableLiveData<Int>()
    private var typeSCounter = MutableLiveData<Int>()
    private var typeCCounter = MutableLiveData<Int>()

    fun getOptions() : PersonalityOption {
        return options[indexAt.value?.toInt()!!]
    }

    fun getIndex(): MutableLiveData<Int> = indexAt

    fun getLengthQuestion(): Int = options.size

    fun increaseIndex() {
        indexAt.value = if (indexAt.value == options.size - 1) indexAt.value else indexAt.value?.plus(1)
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



}