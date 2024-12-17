package com.studay.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.studay.app.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is null. Fragment is not attached or onDestroyView() has been called.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listener for "Membaca" card
        binding.cvMembaca.setOnClickListener {
            val intent = Intent(requireContext(), MembacaActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for "Menulis" card
        binding.cvMenulis.setOnClickListener {
            val intent = Intent(requireContext(), MenulisActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for "Menghitung" card
        binding.cvMenghitung.setOnClickListener {
            val intent = Intent(requireContext(), MenghitungActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

