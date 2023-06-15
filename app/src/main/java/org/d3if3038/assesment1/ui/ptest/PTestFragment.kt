package org.d3if3038.assesment1.ui.ptest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import org.d3if3038.assesment1.R
import org.d3if3038.assesment1.data.SettingDataStore
import org.d3if3038.assesment1.data.dataStore
import org.d3if3038.assesment1.databinding.FragmentPtestBinding
import org.d3if3038.assesment1.db.PersonalityDb
import org.d3if3038.assesment1.network.ApiStatus

class PTestFragment : Fragment() {
    private lateinit var binding: FragmentPtestBinding

    private val viewModel: PTestViewModel by lazy {
        val db = PersonalityDb.getInstance(requireContext())
        val factory = PTestViewModelFactory(db.dao)

        ViewModelProvider(this, factory)[PTestViewModel::class.java]
    }
    private val personalityTestArgs:PTestFragmentArgs by navArgs()
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
        binding = FragmentPtestBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.nextAnswerBtn.setOnClickListener {
            if (binding.radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(context, getString(R.string.pilih_jawaban), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            recordAnswer()
            nextQuestion()
        }
        viewModel.getApiStatus().observe(viewLifecycleOwner) {
            if (it == null) return@observe

            when(it) {
                ApiStatus.LOADING -> {}
                ApiStatus.FAILED -> {
                    binding.progressBar.visibility = View.GONE
                    binding.networkError.visibility = View.VISIBLE
                }
                ApiStatus.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.networkError.visibility = View.GONE

                    binding.radioGroup.visibility = View.VISIBLE
                    binding.nextAnswerBtn.visibility = View.VISIBLE

                    updateQuestion()
                    updateCounter(0)
                }
            }
        }
        viewModel.getIndex().observe(viewLifecycleOwner) {
            if (it == 0) return@observe

            val questionMaxNumber = viewModel.getQuestionLength()

            if (it == questionMaxNumber - 1) binding.nextAnswerBtn.text = getString(R.string.selesai)
            if (it == questionMaxNumber) showResult()

            if (it < questionMaxNumber) updateQuestion()
            updateCounter(it)
        }
    }

    private fun nextQuestion() {
        viewModel.increaseIndex()
    }

    private fun updateCounter(index: Int) {
        binding.answerCounterText.text = getString(
            R.string.answer_counter,
            index + 1,
            viewModel.getQuestionLength())
    }

    private fun showResult() {
        if (settingDataStore.getBoolean(getString(R.string.auto_save_prefrences_key), true)) {
            viewModel.persistPersonalityData(
                personalityTestArgs.fullName,
                personalityTestArgs.age,
                personalityTestArgs.isMale,
                viewModel.getResult()
            )
        }

        findNavController().navigate(
            PTestFragmentDirections.actionPTestFragmentToResultFragment(
                personalityTestArgs.fullName,
                personalityTestArgs.age,
                personalityTestArgs.isMale,
                viewModel.getResult()
            )
        )
    }

    private fun recordAnswer() {
        when(binding.radioGroup.checkedRadioButtonId) {
            R.id.answer_0 -> {
                viewModel.increaseDPoint()
            }
            R.id.answer_1 -> {
                viewModel.increaseIPoint()
            }
            R.id.answer_2 -> {
                viewModel.increaseSPoint()
            }
            R.id.answer_3 -> {
                 viewModel.increaseCPoint()
            }
        }
    }

    private fun updateQuestion() {
        binding.radioGroup.clearCheck()

        val options = viewModel.getOptions()
        binding.answer0.text = options.typeD
        binding.answer1.text = options.typeI
        binding.answer2.text = options.typeS
        binding.answer3.text = options.typeC
    }


}