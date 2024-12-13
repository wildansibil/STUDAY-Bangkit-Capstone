package com.studay.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.studay.app.api.ApiResponse
import com.studay.app.api.ApiService
import com.studay.app.api.RetrofitClient
import com.studay.app.api.dataclass.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var namaInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var registerButton: Button
    private lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize the views
        namaInput = findViewById(R.id.input_login_Nama)
        emailInput = findViewById(R.id.input_login_Email)
        passwordInput = findViewById(R.id.input_login_password)
        confirmPasswordInput = findViewById(R.id.input_login_password2)
        registerButton = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin2)

        // Handle the login TextView click event to navigate to LoginActivity
        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close RegisterActivity
        }

        // Handle Register button click
        registerButton.setOnClickListener {
            val nama = namaInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()

            // Validate the inputs
            when {
                nama.isEmpty() -> showToast("Nama pengguna wajib diisi")
                email.isEmpty() -> showToast("Email wajib diisi")
                password.isEmpty() -> showToast("Password wajib diisi")
                confirmPassword.isEmpty() -> showToast("Konfirmasi password wajib diisi")
                password != confirmPassword -> showToast("Password tidak cocok")
                password.length < 8 -> showToast("Password harus memiliki minimal 8 karakter")
                else -> {
                    // Register the user
                    registerUser(nama, email, password)
                }
            }
        }
    }

    // Function to handle user registration
    private fun registerUser(nama: String, email: String, password: String) {
        val apiService = RetrofitClient.getInstance().create(ApiService::class.java)
        val user = User(nama, email, password)
        val call = apiService.registerUser(user)

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                println("Response Code: ${response.code()}")
                println("Response Body: ${response.body()?.message}")
                println("Error Body: ${response.errorBody()?.string()}")

                if (response.isSuccessful && response.body()?.error == false) {
                    val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("email", email)
                        putString("token", response.body()?.message) // Simpan token jika ada
                        apply()
                    }

                    // Navigate to LoginActivity
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showToast("Registrasi gagal: ${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                println("Failure: ${t.message}")
                showToast("Error: ${t.message}")
            }
        })
    }



    // Function to show Toast messages
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
