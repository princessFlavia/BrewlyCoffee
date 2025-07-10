package com.example.brewlycoffee.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brewlycoffee.databinding.ItemCoffeeBinding
import com.example.brewlycoffee.ui.models.CoffeeItem

class CoffeeItemAdapter(
    private val coffeeItems: List<CoffeeItem>,
    private val onItemClick: (CoffeeItem) -> Unit
) : RecyclerView.Adapter<CoffeeItemAdapter.CoffeeItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeItemViewHolder {
        val binding = ItemCoffeeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoffeeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoffeeItemViewHolder, position: Int) {
        holder.bind(coffeeItems[position])
    }

    override fun getItemCount(): Int = coffeeItems.size

    inner class CoffeeItemViewHolder(
        private val binding: ItemCoffeeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coffeeItem: CoffeeItem) {
            binding.textViewCoffeeName.text = coffeeItem.name
            binding.textViewCoffeePrice.text = coffeeItem.price
            binding.imageViewCoffee.setImageResource(coffeeItem.imageResId)

            binding.root.setOnClickListener {
                onItemClick(coffeeItem)
            }
        }
    }
}