package com.studay.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.adapter.MembacaAdapter2
import com.studay.app.adapter.MenulisAdapter
import com.studay.app.api.dataclass.Huruf
import com.studay.app.api.dataclass.SukuKata
import com.studay.app.databinding.ActivityMenulisBinding
import java.nio.ByteBuffer

class MenulisActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMenulisBinding
    private lateinit var rvAbjad: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var drawingView: DrawingView
    private lateinit var menulisHelper: MenulisHelper
    private lateinit var btnCek: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menulis)

        // Menghubungkan tampilan
        rvAbjad = findViewById(R.id.rvAbjad)
        progressBar = findViewById(R.id.progressBar)
        drawingView = findViewById(R.id.drawingview)
        btnCek = findViewById(R.id.btnCek)

        // Inisialisasi helper dan model
        menulisHelper = MenulisHelper(this)
        menulisHelper.loadModel()

        // Setup RecyclerView dan data huruf
        setupRecyclerView()
        loadSukuKataData()

        // Setup DrawingView
        setupDrawingView()

        // Setup tombol cek untuk memulai inferensi
        btnCek.setOnClickListener {
            // Tampilkan progress bar saat proses dimulai
            progressBar.visibility = View.VISIBLE

            // Mengambil gambar yang digambar di drawing view
            val imageByteBuffer = drawingView.convertToByteBuffer()

            // Menjalankan inferensi model TensorFlow Lite
            runInference(imageByteBuffer)

            // Menyembunyikan progress bar setelah inferensi selesai
            progressBar.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        rvAbjad.layoutManager = GridLayoutManager(this, 2)
    }

    // Fungsi untuk memuat data sukukata A-Z secara manual
    private fun loadSukuKataData() {
        progressBar.visibility = View.VISIBLE

        val sukukataList = getSukuKataList() // Mendapatkan list sukukata A-Z secara manual

        // Mengatur adapter untuk RecyclerView
        rvAbjad.adapter = MembacaAdapter2(sukukataList) { sukukata ->
            Toast.makeText(this@MenulisActivity2, "SukuKata: ${sukukata.sukukata}", Toast.LENGTH_SHORT).show()
        }


        progressBar.visibility = View.GONE
    }

    // Fungsi untuk mendapatkan daftar suku kata
    private fun getSukuKataList(): List<SukuKata> {
        val sukuKataList = mutableListOf<SukuKata>()
        val konsonan = listOf("B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z")

        // Menambahkan suku kata berdasarkan konsonan dan vokal
        for (konsonanSukuKata in konsonan) {
            sukuKataList.add(SukuKata(sukukata = "$konsonanSukuKata A", suara = ""))
            sukuKataList.add(SukuKata(sukukata = "$konsonanSukuKata E", suara = ""))
            sukuKataList.add(SukuKata(sukukata = "$konsonanSukuKata I", suara = ""))
            sukuKataList.add(SukuKata(sukukata = "$konsonanSukuKata O", suara = ""))
            sukuKataList.add(SukuKata(sukukata = "$konsonanSukuKata U", suara = ""))
        }

        return sukuKataList
    }

    private fun setupDrawingView() {
    }

    private fun runInference(input: ByteBuffer) {
        // Menjalankan inferensi menggunakan model
        val output = menulisHelper.runInference(input)

        // Proses output dan tampilkan kelas prediksi
        val predictedClass = getPredictedClass(output)
        Toast.makeText(this, "Prediksi: $predictedClass", Toast.LENGTH_SHORT).show()
    }

    private fun getPredictedClass(output: ByteBuffer): String {
        output.rewind()
        val probabilities = FloatArray(10) // Misalnya, model prediksi 10 kelas
        output.asFloatBuffer().get(probabilities)

        // Ambil kelas dengan probabilitas tertinggi
        val maxIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1
        return if (maxIndex != -1) "Kelas: $maxIndex" else "Tidak dikenal"
    }

    override fun onDestroy() {
        super.onDestroy()
        menulisHelper.close()  // Pastikan untuk menutup model saat Activity dihancurkan
    }
}