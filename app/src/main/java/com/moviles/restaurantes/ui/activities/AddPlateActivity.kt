package com.moviles.restaurantes.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.moviles.restaurantes.R
import com.moviles.restaurantes.databinding.ActivityAddPlateBinding
import com.moviles.restaurantes.databinding.ActivityAddRestaurantBinding
import com.moviles.restaurantes.models.Plate
import com.moviles.restaurantes.repositories.MenuRepository
import com.moviles.restaurantes.repositories.SharedPrefManager

class AddPlateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPlateBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddPlateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSubmitPlate.setOnClickListener {
            // Get the data from the form
            val name = binding.txtPlateNameText.text.toString()
            val description = binding.txtPlateDescription.text.toString()
            val price = binding.txtPlatePrice.text.toString()
            val menu_id = intent.getIntExtra("menu_id", -1)
            val token = SharedPrefManager(this).getAccessToken()

            val plate = Plate(name = name, description = description, price = price, menu_category_id = menu_id!!.toInt())
            //Toast.makeText(this, "name:$name,description $description , price:$price, menu_id : $menu_id, token:$token", Toast.LENGTH_SHORT).show()


            if (token != null) {
                MenuRepository.createPlate(token, plate, success = {
                    Toast.makeText(this, "Plate created successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }, failure = {
                    Toast.makeText(this, "Error creating plate", Toast.LENGTH_SHORT).show()
                })
            }

        }
    }
}