package com.moviles.restaurantes.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.moviles.restaurantes.R
import com.moviles.restaurantes.databinding.ActivityMenuBinding
import com.moviles.restaurantes.databinding.ActivityRegisterBinding
import com.moviles.restaurantes.models.User
import com.moviles.restaurantes.repositories.AuthRepository

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnsubmitRegistration.setOnClickListener {
            val name = binding.txtNameRegister.text.toString()
            val email = binding.txtEmailRegistration.text.toString()
            val password = binding.txtRegistrationPassword.text.toString()
            val phone = binding.txtPhoneRegistration.text.toString()

            val user = User(name, email, password, phone)
            AuthRepository.register(user,
                success = { response ->
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    finish()
                },
                failure = { error ->
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}