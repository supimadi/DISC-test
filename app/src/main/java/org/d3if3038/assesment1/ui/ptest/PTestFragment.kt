package org.d3if3038.assesment1.ui.ptest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.d3if3038.assesment1.databinding.FramgnetPtestBinding

class PTestFragment : Fragment() {
    private lateinit var binding: FramgnetPtestBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FramgnetPtestBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}