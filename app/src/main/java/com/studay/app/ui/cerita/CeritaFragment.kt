package com.studay.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.adapter.CeritaAdapter
import com.studay.app.api.ApiService
import com.studay.app.api.RetrofitClient
import com.studay.app.api.dataclass.Cerita
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CeritaFragment : Fragment() {

    private lateinit var rvCerita: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cerita, container, false)
        rvCerita = view.findViewById(R.id.rvCerita)
        progressBar = view.findViewById(R.id.loadingCerita)

        setupRecyclerView()
        fetchCeritaData()

        return view
    }

    private fun setupRecyclerView() {
        rvCerita.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun fetchCeritaData() {
        progressBar.visibility = View.VISIBLE
        val apiService = RetrofitClient.getInstance().create(ApiService::class.java)
        val token = "Bearer YOUR_ACCESS_TOKEN" // Replace with your token
        val call = apiService.getCerita(token)

        call.enqueue(object : Callback<List<Cerita>> {
            override fun onResponse(call: Call<List<Cerita>>, response: Response<List<Cerita>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val ceritaList = response.body() ?: emptyList()
                    rvCerita.adapter = CeritaAdapter(ceritaList) { cerita ->
                        val intent = Intent(requireContext(), DetailCeritaActivity::class.java).apply {
                            putExtra("judul", cerita.judul)
                            putExtra("deskripsi", cerita.deskripsi)
                            putExtra("gambar", cerita.gambar)
                        }
                        startActivity(intent)
                    }
                } else {
                    showToast("Failed to fetch stories: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Cerita>>, t: Throwable) {
                progressBar.visibility = View.GONE
                showToast("Error: ${t.message}")
            }
        })
    }

    // Function to show Toast messages
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
