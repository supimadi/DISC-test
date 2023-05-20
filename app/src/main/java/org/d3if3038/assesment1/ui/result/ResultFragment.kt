package org.d3if3038.assesment1.ui.result

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import org.d3if3038.assesment1.R
import org.d3if3038.assesment1.data.SettingDataStore
import org.d3if3038.assesment1.data.dataStore
import org.d3if3038.assesment1.databinding.FragmentResultBinding
import org.d3if3038.assesment1.db.PersonalityDb
import org.d3if3038.assesment1.model.personality.PersonalityCategories

class ResultFragment : Fragment() {
    private lateinit var binding : FragmentResultBinding

    private var personalityType = ""
    private var personalityExpl = ""
    private var  fullName = ""
    private var age = 0

    private val resultArgs: ResultFragmentArgs by navArgs()
    private val viewModel: ResultViewModel by lazy {
        val db = PersonalityDb.getInstance(requireContext())
        val factory = ResultViewModelFactory(db.dao)

        ViewModelProvider(this, factory)[ResultViewModel::class.java]
    }
    private val settingDataStore: SettingDataStore by lazy {
        SettingDataStore(requireContext().dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fullName = resultArgs.fullName
        age = resultArgs.age

        binding.resultNameTextView.text = fullName
        binding.shareButton.setOnClickListener { sharePersonality() }

        if (settingDataStore.getBoolean(getString(R.string.auto_save_prefrences_key), false)) {
            persistPersonality()
            binding.saveButton.visibility = View.INVISIBLE
        } else {
            binding.saveButton.setOnClickListener { persistPersonality() }
        }

        when (resultArgs.personalityType) {
            PersonalityCategories.TYPE_D -> {
                personalityType = getString(R.string.type_d)
                personalityExpl = getString(R.string.personality_d)
                binding.personalityImage.setImageResource(R.drawable.personality_dominance)
            }
            PersonalityCategories.TYPE_I -> {
                personalityType = getString(R.string.type_i)
                personalityExpl = getString(R.string.personality_i)
                binding.personalityImage.setImageResource(R.drawable.personality_influence)
            }
            PersonalityCategories.TYPE_S -> {
                personalityType = getString(R.string.type_s)
                personalityExpl = getString(R.string.personality_s)
                binding.personalityImage.setImageResource(R.drawable.personality_steady)
            }
            PersonalityCategories.TYPE_C -> {
                personalityType = getString(R.string.type_c)
                personalityExpl = getString(R.string.personality_c)
                binding.personalityImage.setImageResource(R.drawable.personality_conscientiousness)
            }
        }

        binding.personalityTitleTextView.text = personalityType
        binding.explanationTextView.text = personalityExpl
    }

    private fun sharePersonality() {
        val message = getString(R.string.share_template,
            fullName,
            age,
            personalityType,
            personalityExpl
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }

    }

    private fun persistPersonality() {
        viewModel.persistPersonalityData(
            fullName,
            age,
            resultArgs.isMale,
            resultArgs.personalityType
        )

        Toast.makeText(requireContext(), getString(R.string.success_persist_personality_data), Toast.LENGTH_LONG).show()
        binding.saveButton.isEnabled = false
    }

}