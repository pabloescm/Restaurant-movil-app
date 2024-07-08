package com.moviles.restaurantes.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviles.restaurantes.R
import com.moviles.restaurantes.adapters.UserRestaurantListAdapter
import com.moviles.restaurantes.databinding.ActivityHomeBinding
import com.moviles.restaurantes.databinding.ActivityUserBinding
import com.moviles.viewmodels.UserViewModel

class UserActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserBinding
    private val model: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        model.fetchUserRestaurants(this)
        setupViewModelListeners()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnAddRestaurant.setOnClickListener {
            // Navigate to AddRestaurantActivity
            val Intent = Intent(this, AddRestaurantActivity::class.java)
            startActivity(Intent)
        }
    }

    private fun setupViewModelListeners() {
        model.restaurantList.observe(this, {
            (binding.lstUserRestaurant.adapter as UserRestaurantListAdapter).updateData(it)
        })
    }

    private fun setupRecyclerView() {
        val adapter = UserRestaurantListAdapter(arrayListOf(),model)
        binding.lstUserRestaurant.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@UserActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the list of restaurants
        model.fetchUserRestaurants(this)
    }
}