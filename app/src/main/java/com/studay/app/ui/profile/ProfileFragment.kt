package com.studay.app

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.studay.app.database.StudayDao
import com.studay.app.database.StudayDatabase
import com.studay.app.databinding.FragmentProfileBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is null. Fragment is not attached or onDestroyView() has been called.")

    private lateinit var userDao: StudayDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val db = Room.databaseBuilder(requireContext(), StudayDatabase::class.java, "user_database").build()
        userDao = db.userDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil ID pengguna dari SharedPreferences atau token yang sudah disimpan
        val userId = "unique_user_id" // Ambil ID pengguna dari SharedPreferences atau token yang disimpan

        GlobalScope.launch {
            val user = userDao.getUserByEmail(userId)
            user?.let {
                // Update UI dengan data pengguna
                binding.tvNama.text = it.name
                binding.tvEmail.text = it.email
            }
        }

        // Tombol logout
        binding.btnLogout.setOnClickListener {
            // Hapus data pengguna di Room
            GlobalScope.launch {
                val user = userDao.getUserByEmail(userId)
                user?.let {
                    userDao.deleteUser(it)
                }
            }

            // Hapus token dan data dari SharedPreferences
            val sharedPref = requireActivity().getSharedPreferences("user_preferences", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()  // Hapus data login
            editor.apply()

            // Pindah ke halaman login
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}

