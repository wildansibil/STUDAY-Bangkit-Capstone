package com.studay.app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import com.studay.app.api.ApiService
import com.studay.app.api.LoginCredentials
import com.studay.app.api.ApiResponse
import com.studay.app.api.RetrofitClient
import com.studay.app.database.StudayDatabase
import com.studay.app.database.UserEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Menghubungkan dengan ID di XML
        emailInput = findViewById(R.id.input_login_email)
        passwordInput = findViewById(R.id.input_login_password)
        loginButton = findViewById(R.id.btnLogin)
        registerText = findViewById(R.id.tvRegister2)

        // Menginisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)

        // Tombol login
        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty()) {
                showToast("Email wajib diisi!")
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                showToast("Password wajib diisi!")
                return@setOnClickListener
            }

            // Melakukan request login ke API
            val loginCredentials = LoginCredentials(email, password)
            loginUser(loginCredentials)
        }

        // Teks register untuk intent ke RegisterActivity
        registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(credentials: LoginCredentials) {
        val apiService = RetrofitClient.getInstance(context = this@LoginActivity).create(ApiService::class.java) // Berikan konteks di sini
        val call = apiService.loginUser(credentials)

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.error == false) {
                        // Menyimpan token ke SharedPreferences
                        val editor = sharedPreferences.edit()
                        editor.putString("token", apiResponse.message)
                        editor.apply()

                        // Menyimpan data pengguna ke Room
                        saveUserToDatabase(credentials.email, credentials.password, apiResponse.message)

                        // Berpindah ke MainActivity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        showToast("Login gagal: ${apiResponse?.message}")
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun saveUserToDatabase(email: String, password: String, token: String) {
        val db = Room.databaseBuilder(this, StudayDatabase::class.java, "user_database").build()
        val userDao = db.userDao()

        val userEntity = UserEntity(
            email = email,
            name = "Unknown", // Ganti dengan data yang sesuai jika ada
            password = password, // Pastikan password sudah terenkripsi sebelum disimpan
            token = token
        )

        // Simpan ke Room Database di dalam lifecycleScope
        lifecycleScope.launch {
            userDao.insert(userEntity)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
