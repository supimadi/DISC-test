package org.d3if3038.assesment1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.d3if3038.assesment1.databinding.ActivityPersonalityTestBinding

class PersonalityTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalityTestBinding

    // Types of personality
    private val typeD = listOf("Forceful", "Pioneering", "Bold", "Argumentative", "Daring", "Self-reliant", "Decisive", "Assertive", "Unyielding", "Persistent", "Relentless", "Strong-Willed", "Adventurous", "Aggressive", "Determined", "Commanding", "Force-of-character", "Independent", "Out-spoken", "Impatient", "Competitive", "Courageous", "Pushy", "Directing")
    private val typeI = listOf("Expressive", "Exciting", "Animated", "Unpredictable", "Out-Going", "Persuasive", "Life-of-the-party", "Popular", "Colorful", "Optimistic", "Talkative", "Playful", "Charming", "Attractive", "Enthusiastic", "Impulsive", "Lively", "Influential", "Popular", "Emotional", "Spontaneous", "Convincing", "Flighty", "Stimulating")
    private val typeS = listOf("Restrained", "Satisfied", "Willing", "Indecisive", "Patient", "Gentle", "Even-tempered", "Generous", "Easy-going", "Accommodating", "Neighborly", "Friendly", "Deliberate", "Steady", "Sympathetic", "Slow-paced", "Laid-back", "Kind", "Pleasant", "Procrastinator", "Loyal", "Considerate", "Dependent", "Tolerant")
    private val typeC = listOf("Careful", "Correct", "Precise", "Doubting", "Respectful", "Logical", "Cautious", "Perfectionist", "Modest", "Systematic", "Humble", "Observant", "Disciplined", "Restrained", "Analytical", "Critical", "Consistent", "Orderly", "Idealistic", "Serious", "Thoughtful", "Self-sacrificing", "Stoic", "Conventional")

    private var indexAt = 0
    private var typeDCounter = 0
    private var typeICounter = 0
    private var typeSCounter = 0
    private var typeCCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonalityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextAnswerBtn.setOnClickListener { nextAnswer() }
        binding.answerCounterText.text = getString(R.string.answer_counter, indexAt + 1, typeD.size)

        setAnswer(indexAt)
    }

    private fun maxVal(first: Int, second: Int, third: Int, fourth: Int): Boolean {
        return first > second && first > third && first > fourth
    }

    private fun doneTakingTest() {
        var typePersonality = ""

        if (maxVal(typeDCounter, typeICounter, typeSCounter, typeCCounter)) {
            binding.traitPersonality.text = getString(R.string.personality_d)
            typePersonality = "Dominance"
        } else if (maxVal(typeICounter, typeDCounter, typeSCounter, typeCCounter)) {
            binding.traitPersonality.text = getString(R.string.personality_i)
            typePersonality = "Influence"
        } else if (maxVal(typeSCounter, typeDCounter, typeICounter, typeCCounter)) {
            binding.traitPersonality.text = getString(R.string.personality_s)
            typePersonality = "Steadiness"
        } else {
            binding.traitPersonality.text = getString(R.string.personality_c)
            typePersonality = "Conscientiousness"
        }

        binding.personalityText.text = getString(
            R.string.kesimpulan,
            intent.getStringExtra("fullName"),
            intent.getStringExtra("age"),
            typePersonality
        )
    }

    private fun nextAnswer() {
        // menandakan user telah selesai mengambil test
        if (indexAt < 0) return

        if (binding.radioGroup.checkedRadioButtonId < 0) {
            Toast.makeText(this, R.string.answer_not_selected, Toast.LENGTH_LONG).show()
            return;
        }

        if(binding.answer0.isChecked) {
            typeDCounter++
        } else if (binding.answer1.isChecked) {
            typeICounter++
        } else if(binding.answer2.isChecked) {
            typeSCounter++
        } else {
            typeCCounter++
        }

        indexAt = if(indexAt == typeD.size - 1) typeD.size - 1 else indexAt + 1
        binding.answerCounterText.text = getString(R.string.answer_counter, indexAt + 1, typeD.size)

        if (indexAt == typeD.size - 2)
            binding.nextAnswerBtn.text = getString(R.string.done)

        if (indexAt == typeD.size - 1) {
            doneTakingTest()
            indexAt = -1
            return
        }

        binding.radioGroup.clearCheck()
        setAnswer(indexAt)
    }

    private fun setAnswer(index: Int) {
        binding.answer0.text = typeD[index]
        binding.answer1.text = typeI[index]
        binding.answer2.text = typeS[index]
        binding.answer3.text = typeC[index]
    }
}