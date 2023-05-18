package org.d3if3038.assesment1.ui.history
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if3038.assesment1.R
import org.d3if3038.assesment1.databinding.ItemHistoryBinding
import org.d3if3038.assesment1.db.PersonalityEntity
import org.d3if3038.assesment1.model.personality.PersonalityCategories
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : ListAdapter<PersonalityEntity, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<PersonalityEntity>() {
                override fun areItemsTheSame(
                    oldItem: PersonalityEntity,
                    newItem: PersonalityEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: PersonalityEntity,
                    newItem: PersonalityEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormater = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )

        fun bind(item: PersonalityEntity) = with(binding) {
            val gender = root.context.getString(
                if (item.isMale) R.string.pria else R.string.wanita
            )
            var personalityType = ""
            var colorRes = 0

            when(item.personalityType) {
                PersonalityCategories.TYPE_D -> {
                    personalityType = root.context.getString(R.string.type_d)
                    colorRes = ContextCompat.getColor(root.context, R.color.color_d)
                }
                PersonalityCategories.TYPE_I -> {
                    personalityType = root.context.getString(R.string.type_i)
                    colorRes = ContextCompat.getColor(root.context, R.color.color_i)
                }
                PersonalityCategories.TYPE_S -> {
                    personalityType = root.context.getString(R.string.type_s)
                    colorRes = ContextCompat.getColor(root.context, R.color.color_s)
                }
                PersonalityCategories.TYPE_C -> {
                    personalityType = root.context.getString(R.string.type_c)
                    colorRes = ContextCompat.getColor(root.context, R.color.color_c)
                }
            }

            val circleBg = categoryTextView.background as GradientDrawable
            circleBg.setColor(colorRes)

            categoryTextView.text = item.personalityType.toString().substring(5, 6)
            dateTextView.text = dateFormater.format(Date(item.date))
            nameTextView.text = root.context.getString(R.string.name_history_template,
                item.fullName,
                item.age,
                gender
            )
            personalityTypeTextView.text = root.context.getString(R.string.result_history_template,
                personalityType
            )
        }
    }

}