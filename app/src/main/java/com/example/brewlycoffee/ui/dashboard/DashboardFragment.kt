package com.example.brewlycoffee.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brewlycoffee.R
import com.example.brewlycoffee.databinding.FragmentDashboardBinding
import com.example.brewlycoffee.ui.adapters.CategoryAdapter
import com.example.brewlycoffee.ui.adapters.CoffeeItemAdapter
import com.example.brewlycoffee.ui.models.CoffeeItem
import androidx.core.widget.doOnTextChanged

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var coffeeItemAdapter: CoffeeItemAdapter

    private var selectedCategory = "Cappuccino"
    private var allCoffeeItems = mutableListOf<CoffeeItem>()
    private var filteredItems = mutableListOf<CoffeeItem>()

    private val categories = listOf(
        "Cappuccino", "Macchiato", "Latte", "Espresso",
        "Americano", "Mocha", "Frappuccino"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
        setupRecyclerViews()
        setupSearchListener()
        filterItems()
    }

    private fun setupData() {
        allCoffeeItems = mutableListOf(
            CoffeeItem(1, "Cappuccino", "$3.00", "Cappuccino", R.drawable.coffee_icon),
            CoffeeItem(2, "Espresso", "$5.00", "Espresso", R.drawable.coffee_icon),
            CoffeeItem(3, "Latte", "$3.00", "Latte", R.drawable.coffee_icon),
            CoffeeItem(4, "Macchiato", "$4.50", "Macchiato", R.drawable.coffee_icon),
            CoffeeItem(5, "Americano", "$2.50", "Americano", R.drawable.coffee_icon),
            CoffeeItem(6, "Mocha", "$4.00", "Mocha", R.drawable.coffee_icon),
            CoffeeItem(7, "Frappuccino", "$5.50", "Frappuccino", R.drawable.coffee_icon),
            CoffeeItem(8, "Iced Latte", "$3.50", "Latte", R.drawable.coffee_icon),)
        )
    }

    private fun setupRecyclerViews() {
        // Setup Categories RecyclerView
        categoryAdapter = CategoryAdapter(categories) { category ->
            selectedCategory = category
            filterItems()
        }

        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        // Setup Coffee Items RecyclerView
        coffeeItemAdapter = CoffeeItemAdapter(filteredItems) { coffeeItem ->
            // Handle item click
            Toast.makeText(context, "Added ${coffeeItem.name} to cart", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerViewCoffeeItems.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = coffeeItemAdapter
        }
    }

    private fun setupSearchListener() {
        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            filterItems(text.toString())
        }
    }

    private fun filterItems(searchQuery: String = "") {
        filteredItems.clear()

        val filtered = allCoffeeItems.filter { item ->
            item.category == selectedCategory &&
                    item.name.contains(searchQuery, ignoreCase = true)
        }

        filteredItems.addAll(filtered.take(3)) // Show only first 3 items
        coffeeItemAdapter.notifyDataSetChanged()

        // Update category selection
        categoryAdapter.updateSelectedCategory(selectedCategory)

        // Show no results message if needed
        if (filteredItems.isEmpty() && searchQuery.isNotEmpty()) {
            binding.textViewNoResults.visibility = View.VISIBLE
            binding.textViewNoResults.text = "No items found for \"$searchQuery\" in $selectedCategory"
        } else {
            binding.textViewNoResults.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}