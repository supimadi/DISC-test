package org.d3if3038.assesment1.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import org.d3if3038.assesment1.R
import org.d3if3038.assesment1.data.SettingDataStore
import org.d3if3038.assesment1.data.dataStore
import org.d3if3038.assesment1.databinding.FragmentResultBinding
import org.d3if3038.assesment1.db.PersonalityDb
import org.d3if3038.assesment1.model.personality.PersonalityCategories
import org.d3if3038.assesment1.network.PersonalityApi

class ResultFragment : Fragment() {
    private lateinit var binding : FragmentResultBinding

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

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setAutoSave(
            settingDataStore.getBoolean(
                getString(R.string.auto_save_prefrences_key),
                true
            )
        )

        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.result_menu, menu)

        val saveMenu = menu.findItem(R.id.menu_result_save)
        saveMenu.isVisible = !viewModel.isAutoSave()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_result_share -> {
                sharePersonality()
                return true
            }
            R.id.menu_result_save -> {
                persistPersonality()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateUI() {
        var personalityType = ""
        var personalityExpl = ""

        when (resultArgs.personalityType) {
            PersonalityCategories.TYPE_D -> {
                personalityType = getString(R.string.type_d)
                personalityExpl = getString(R.string.personality_d)
            }
            PersonalityCategories.TYPE_I -> {
                personalityType = getString(R.string.type_i)
                personalityExpl = getString(R.string.personality_i)
            }
            PersonalityCategories.TYPE_S -> {
                personalityType = getString(R.string.type_s)
                personalityExpl = getString(R.string.personality_s)
            }
            PersonalityCategories.TYPE_C -> {
                personalityType = getString(R.string.type_c)
                personalityExpl = getString(R.string.personality_c)
            }
        }

        Glide.with(binding.personalityImage)
            .load(PersonalityApi.getImagePersonalityTypeUrl(personalityType.lowercase()))
            .error(R.drawable.baseline_broken_image_24)
            .into(binding.personalityImage)

        binding.resultNameTextView.text = resultArgs.fullName
        binding.personalityTitleTextView.text = personalityType
        binding.explanationTextView.text = personalityExpl

        viewModel.setPersonalityExpl(personalityExpl)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun sharePersonality() {
        val message = getString(R.string.share_template,
            binding.resultNameTextView.text,
            resultArgs.age,
            binding.personalityTitleTextView.text,
            binding.explanationTextView.text
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }

    }

    private fun persistPersonality() {
        viewModel.persistPersonalityData(
            resultArgs.fullName,
            resultArgs.age,
            resultArgs.isMale,
            resultArgs.personalityType
        )

        Toast.makeText(requireContext(), getString(R.string.success_persist_personality_data), Toast.LENGTH_LONG).show()
    }

}