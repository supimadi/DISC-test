package org.d3if3038.assesment1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.d3if3038.assesment1.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private lateinit var binding : FragmentResultBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

}