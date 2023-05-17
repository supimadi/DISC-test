package org.d3if3038.assesment1.ui.welcome

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3038.assesment1.R
import org.d3if3038.assesment1.databinding.FragmentWelcomeBinding
import org.d3if3038.assesment1.model.personality.Profile

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding

    private val viewModel: WelcomeViewModel by lazy {
        ViewModelProvider(this)[WelcomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.startButton.setOnClickListener { startPersonalityTest() }
        viewModel.getNavPersonalityTest().observe(viewLifecycleOwner) {
            if (it == null) return@observe
            findNavController().navigate(
                WelcomeFragmentDirections.actionWelcomeFragmentToPTestFragment(
                    it.fullName,
                    it.age,
                    it.isMale
                )
            )
            viewModel.doneNavPersonalityTest()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.welcome_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu__welcome_history -> {
                findNavController().navigate(R.id.action_welcomeFragment_to_historyFragment)
                return true
            }
            R.id.menu_welcome_about -> {
                findNavController().navigate(R.id.action_welcomeFragment_to_aboutFragment)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun startPersonalityTest() {
        val fullName = binding.nameInput.text.toString()
        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(context, R.string.name_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val age = binding.ageInput.text.toString()
        if (TextUtils.isEmpty(age)) {
            Toast.makeText(context, R.string.age_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.startPersonalityTest(
            Profile(
                fullName,
                age.toInt(),
                selectedId == R.id.maleRadioButton)
        )

    }
}