package com.studay.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.adapter.MenulisAdapter
import com.studay.app.api.dataclass.Huruf
import com.studay.app.databinding.ActivityMenulisBinding
import java.nio.ByteBuffer

class MenulisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenulisBinding
    private lateinit var rvAbjad: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var drawingView: DrawingView
    private lateinit var menulisHelper: MenulisHelper
    private lateinit var btnCek: Button
    private lateinit var btnReset: ImageButton  // Tambahkan variabel untuk btnReset

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menulis)

        // Menghubungkan tampilan
        rvAbjad = findViewById(R.id.rvAbjad)
        progressBar = findViewById(R.id.progressBar)
        drawingView = findViewById(R.id.drawingview)
        btnCek = findViewById(R.id.btnCek)
        btnReset = findViewById(R.id.btnReset)  // Inisialisasi btnReset

        // Inisialisasi helper dan model
        menulisHelper = MenulisHelper(this)
        menulisHelper.loadModel()

        // Setup RecyclerView dan data huruf
        setupRecyclerView()
        loadHurufData()

        // Setup DrawingView
        setupDrawingView()

        // Setup tombol cek untuk memulai inferensi
        btnCek.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val imageByteBuffer = drawingView.convertToByteBuffer()
            runInference(imageByteBuffer)
            progressBar.visibility = View.GONE
        }

        // Tambahkan listener untuk tombol reset
        btnReset.setOnClickListener {
            drawingView.resetDrawing()  // Memanggil fungsi reset pada DrawingView
        }
    }

    private fun setupRecyclerView() {
        rvAbjad.layoutManager = GridLayoutManager(this, 2)
    }

    private fun loadHurufData() {
        progressBar.visibility = View.VISIBLE
        val hurufList = getHurufList()
        val randomHuruf = hurufList.random()
        rvAbjad.adapter = MenulisAdapter(listOf(randomHuruf)) { huruf ->
            Toast.makeText(this@MenulisActivity, "Huruf: ${huruf.huruf}", Toast.LENGTH_SHORT).show()
        }
        progressBar.visibility = View.GONE
    }

    private fun getHurufList(): List<Huruf> {
        val hurufList = mutableListOf<Huruf>()
        for (char in 'A'..'Z') {
            hurufList.add(Huruf(huruf = char, suara = String()))
        }
        return hurufList
    }

    private fun setupDrawingView() {
        // Anda bisa menambahkan konfigurasi tambahan untuk DrawingView jika diperlukan
    }

    private fun runInference(input: ByteBuffer) {
        val output = menulisHelper.runInference(input)
        val predictedClass = getPredictedClass(output)
        Toast.makeText(this, "Prediksi: $predictedClass", Toast.LENGTH_SHORT).show()
    }

    private fun getPredictedClass(output: ByteBuffer): String {
        output.rewind()
        val probabilities = FloatArray(10) // Misalnya, model prediksi 10 kelas
        output.asFloatBuffer().get(probabilities)

        val maxIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1
        return if (maxIndex != -1) "Kelas: $maxIndex" else "Tidak dikenal"
    }

    override fun onDestroy() {
        super.onDestroy()
        menulisHelper.close()  // Pastikan untuk menutup model saat Activity dihancurkan
    }
}