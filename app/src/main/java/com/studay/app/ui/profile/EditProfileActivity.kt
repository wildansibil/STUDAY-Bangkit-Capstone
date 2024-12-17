package com.studay.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.studay.app.database.StudayDao
import com.studay.app.database.StudayDatabase
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var saveButton: Button
    private lateinit var userDao: StudayDao

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val db = StudayDatabase.getDatabase(applicationContext)
        userDao = db.userDao()

        // Ambil ID pengguna
        val userId = "unique_user_id"  // Ganti dengan ID pengguna yang sesuai

        nameInput = findViewById(R.id.etNama)
        emailInput = findViewById(R.id.etEmail)
        passwordInput = findViewById(R.id.etPassword)
        confirmPasswordInput = findViewById(R.id.etConfirmPassword)
        saveButton = findViewById(R.id.btnSave)

        // Ambil data profil pengguna dari Room
        lifecycleScope.launch {
            val user = userDao.getUserByEmail(userId)
            user?.let {
                runOnUiThread {
                    nameInput.setText(it.name)
                    emailInput.setText(it.email)
                    // Password tidak bisa ditampilkan, tapi Anda bisa membiarkannya kosong untuk diubah
                    passwordInput.setText("")
                    confirmPasswordInput.setText("")
                }
            }
        }

        // Tombol simpan untuk menyimpan perubahan ke Room
        saveButton.setOnClickListener {
            val newName = nameInput.text.toString()
            val newEmail = emailInput.text.toString()
            val newPassword = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            // Validasi konfirmasi password
            if (newPassword != confirmPassword) {
                Toast.makeText(this, "Password dan konfirmasi password tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan perubahan profil ke Room
            lifecycleScope.launch {
                val user = userDao.getUserByEmail(userId)
                user?.let {
                    // Update user dengan data baru
                    val updatedUser = it.copy(name = newName, email = newEmail, password = newPassword)
                    userDao.update(updatedUser) // Menyimpan perubahan ke database
                    runOnUiThread {
                        Toast.makeText(this@EditProfileActivity, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Kembali ke ProfileFragment atau aktivitas sebelumnya
            finish()
        }
    }
}
