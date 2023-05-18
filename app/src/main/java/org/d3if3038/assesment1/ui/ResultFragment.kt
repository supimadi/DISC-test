package org.d3if3038.assesment1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if3038.assesment1.R
import org.d3if3038.assesment1.databinding.FragmentResultBinding
import org.d3if3038.assesment1.model.personality.PersonalityCategories

class ResultFragment : Fragment() {
    private lateinit var binding : FragmentResultBinding

    private val resultArgs: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.resultNameTextView.text = resultArgs.fullName

        when (resultArgs.personalityType) {
            PersonalityCategories.TYPE_D -> {
                binding.personalityTitleTextView.text = "Dominance"
                binding.explanationTextView.text = getString(R.string.personality_d)
                binding.personalityImage.setImageResource(R.drawable.personality_dominance)
            }
            PersonalityCategories.TYPE_I -> {
                binding.personalityTitleTextView.text = "Influence"
                binding.explanationTextView.text = getString(R.string.personality_i)
                binding.personalityImage.setImageResource(R.drawable.personality_influence)
            }
            PersonalityCategories.TYPE_S -> {
                binding.personalityTitleTextView.text = "Steadiness"
                binding.explanationTextView.text = getString(R.string.personality_s)
                binding.personalityImage.setImageResource(R.drawable.personality_steady)
            }
            PersonalityCategories.TYPE_C -> {
                binding.personalityTitleTextView.text = "Compliance"
                binding.explanationTextView.text = getString(R.string.personality_c)
                binding.personalityImage.setImageResource(R.drawable.personality_conscientiousness)
            }
        }
    }

}