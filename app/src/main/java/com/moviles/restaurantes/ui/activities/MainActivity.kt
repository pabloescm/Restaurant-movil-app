package com.moviles.restaurantes.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.moviles.restaurantes.R
import com.moviles.restaurantes.databinding.ActivityMainBinding
import com.moviles.restaurantes.models.Auth
import com.moviles.restaurantes.repositories.AuthRepository
import com.moviles.restaurantes.repositories.SharedPrefManager

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPrefManager = SharedPrefManager(this)
        val token = sharedPrefManager.getAccessToken()
        Toast.makeText(this, "Token: $token", Toast.LENGTH_SHORT).show()

        if (token != null) {

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners();
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.txtLoginemail.text.toString()
            val password = binding.txtLoginPassword.text.toString()


            val user = Auth(email, password)


            AuthRepository.login(user, this,
                success = { loginResponse ->

                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                failure = { error ->

                    Toast.makeText(this, "Login failed: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
        binding.txtRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.txtLoginGuest.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }
    }
}