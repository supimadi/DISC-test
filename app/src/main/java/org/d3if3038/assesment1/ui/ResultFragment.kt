package org.d3if3038.assesment1.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.d3if3038.assesment1.R
import org.d3if3038.assesment1.databinding.FragmentResultBinding

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
                return true
            }
        }

        return onOptionsItemSelected(item)
    }

}