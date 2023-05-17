package org.d3if3038.assesment1.ui.ptest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.d3if3038.assesment1.R
import org.d3if3038.assesment1.databinding.FragmentPtestBinding

class PTestFragment : Fragment() {
    private lateinit var binding: FragmentPtestBinding
    private var questionMaxNumber: Int = 0

    private val viewModel: PTestViewModel by lazy {
        ViewModelProvider(this)[PTestViewModel::class.java]
    }
    private val personalityTestArgs:PTestFragmentArgs by navArgs()

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
            recordAnswer()
            nextQuestion()
        }
        viewModel.getIndex().observe(viewLifecycleOwner) {
            updateCounter(it)

            if (it == questionMaxNumber - 1) binding.nextAnswerBtn.text = "Selesai"
            if (it < questionMaxNumber) updateQuestion()
            if (it == questionMaxNumber) showResult()
        }

        updateQuestion()
        questionMaxNumber = viewModel.getLengthQuestion()
    }

    private fun nextQuestion() {
        viewModel.increaseIndex()
    }

    private fun updateCounter(index: Int) {
        binding.answerCounterText.text = getString(
            R.string.answer_counter,
            index + 1,
            questionMaxNumber)
    }

    private fun showResult() {
        Log.d("FRAGMENT_SWITCH", "Change fragment")
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
                return
            }
            R.id.answer_1 -> {
                viewModel.increaseIPoint()
                return
            }
            R.id.answer_2 -> {
                viewModel.increaseSPoint()
                return
            }
            R.id.answer_3 -> {
                viewModel.increaseCPoint()
                return
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