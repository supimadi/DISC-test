package org.d3if3038.assesment1.ui.ptest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        binding.nextAnswerBtn.setOnClickListener { nextQuestion() }
        viewModel.getIndex().observe(viewLifecycleOwner) {
            updateQuestion()
            updateCounter(it)
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

    private fun updateQuestion() {
        val options = viewModel.getOptions()
        binding.answer0.text = options.typeD
        binding.answer1.text = options.typeI
        binding.answer2.text = options.typeS
        binding.answer3.text = options.typeC
    }


}