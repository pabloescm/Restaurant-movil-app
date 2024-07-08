package com.moviles.restaurantes.ui.activities


import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.moviles.restaurantes.databinding.ActivityUploadLogoBinding
import com.moviles.restaurantes.repositories.SharedPrefManager
import com.moviles.viewmodels.UploadLogoViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class UploadLogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadLogoBinding
    private lateinit var viewModel: UploadLogoViewModel
    private var restaurantId: Int = -1
    private var filePart: MultipartBody.Part? = null

    private val fileSelectionLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { selectedFileUri ->
            val inputStream = contentResolver.openInputStream(selectedFileUri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            if (bytes != null) {
                val requestBody: RequestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())

                filePart = MultipartBody.Part.createFormData("image", "logo_file.jpg", requestBody)
                Toast.makeText(this, "File selected successfully", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Failed to read file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadLogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[UploadLogoViewModel::class.java]

        restaurantId = intent.getIntExtra("restaurantId", -1)
        if (restaurantId == -1) {
            Toast.makeText(this, "Restaurant ID not found", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Restaurant ID: $restaurantId", Toast.LENGTH_SHORT).show()
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.selectFileButton.setOnClickListener {
            fileSelectionLauncher.launch("*/*")
        }

        binding.uploadFileButton.setOnClickListener {
            val token = SharedPrefManager(this).getAccessToken()

            if (token.isNullOrEmpty()) {
                Toast.makeText(this, "Token is invalid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (restaurantId == -1) {
                Toast.makeText(this, "Restaurant ID is invalid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (filePart == null) {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.uploadLogo(token, restaurantId, filePart!!,
                success = {
                    Toast.makeText(this, "File uploaded successfully", Toast.LENGTH_SHORT).show()
                    finish()
                },
                failure = { throwable ->
                    Toast.makeText(this, "Failed to upload file: ${throwable.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
