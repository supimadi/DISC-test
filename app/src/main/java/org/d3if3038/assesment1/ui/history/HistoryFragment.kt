package org.d3if3038.assesment1.ui.history

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if3038.assesment1.R
import org.d3if3038.assesment1.databinding.FragmentHistoryBinding
import org.d3if3038.assesment1.db.PersonalityDb

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    private val viewModel : HistoryViewModel by lazy {
        val db = PersonalityDb.getInstance(requireContext())
        val factory = HistoryViewModelFactory(db.dao)

        ViewModelProvider(this, factory)[HistoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        historyAdapter = HistoryAdapter()

        setHasOptionsMenu(true)

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deletePersonalityData(viewHolder.adapterPosition)
            }

        }

        with(binding.historyRecycleView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = historyAdapter

            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)

            setHasFixedSize(true)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.personalityData.observe(viewLifecycleOwner) {
            binding.emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE

            historyAdapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_history_delete -> {
                deleteAllPersonalityDataDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllPersonalityDataDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.delete_all_confirmation))
            .setPositiveButton(getString(R.string.hapus_semua)) {_, _ ->
                viewModel.deleteAllPersonalityData()
            }
            .setNegativeButton(getString(R.string.batal)) {dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}