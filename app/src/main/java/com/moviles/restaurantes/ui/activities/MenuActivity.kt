package com.moviles.restaurantes.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviles.restaurantes.R
import com.moviles.restaurantes.adapters.MenuListAdapter
import com.moviles.restaurantes.databinding.ActivityMainBinding
import com.moviles.restaurantes.databinding.ActivityMenuBinding
import com.moviles.viewmodels.MenuViewModel

class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    private val model: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val restaurantId = intent.getIntExtra("restaurantId", -1)
        Toast.makeText(this, "Restaurant ID: $restaurantId", Toast.LENGTH_SHORT).show()
        model.fetchRestaurantMenu(restaurantId)
        model.menuList.observe(this, { menus ->
            (binding.lstUserMenu.adapter as MenuListAdapter).updateData(menus)

        })

        setupRecyclerView()
        setupListeners()

    }

    private fun setupListeners() {
        binding.btnAddMenu.setOnClickListener {

            val restaurantId = intent.getIntExtra("restaurantId", -1) // Retrieve the restaurantId from the intent that started this activity
            val addMenuIntent = Intent(this, AddMenuActivity::class.java)
            addMenuIntent.putExtra("restaurantId", restaurantId)
            startActivity(addMenuIntent)
        }
    }

    private fun setupRecyclerView() {
        val adapter = MenuListAdapter(arrayListOf())
        binding.lstUserMenu.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@MenuActivity)
        }

    }

    override fun onResume() {
        super.onResume()
        model.fetchRestaurantMenu(intent.getIntExtra("restaurantId", -1))

    }


}