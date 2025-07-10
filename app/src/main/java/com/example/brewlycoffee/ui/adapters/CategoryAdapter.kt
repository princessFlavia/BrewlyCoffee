package com.example.brewlycoffee.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brewlycoffee.R
import com.example.brewlycoffee.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<String>,
    private val onCategorySelected: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedCategory = "Cappuccino"

    fun updateSelectedCategory(category: String) {
        val previousIndex = categories.indexOf(selectedCategory)
        val newIndex = categories.indexOf(category)

        selectedCategory = category

        if (previousIndex != -1) notifyItemChanged(previousIndex)
        if (newIndex != -1) notifyItemChanged(newIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) {
            binding.textViewCategory.text = category

            val isSelected = category == selectedCategory

            if (isSelected) {
                binding.textViewCategory.setBackgroundResource(R.drawable.category_selected_background)
                binding.textViewCategory.setTextColor(
                    binding.root.context.getColor(android.R.color.white)
                )
            } else {
                binding.textViewCategory.setBackgroundResource(R.drawable.category_unselected_background)
                binding.textViewCategory.setTextColor(
                    binding.root.context.getColor(R.color.text_primary)
                )
            }

            binding.root.setOnClickListener {
                onCategorySelected(category)
            }
        }
    }
}