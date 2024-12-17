package com.studay.app.adapter

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.R
import com.studay.app.api.TtsRequest
import com.studay.app.api.TtsRetrofitClient
import com.studay.app.api.dataclass.Huruf
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class MembacaAdapter(
    private val hurufList: List<Huruf>,
    private val onItemClick: (Huruf) -> Unit
) : RecyclerView.Adapter<MembacaAdapter.HurufViewHolder>() {

    inner class HurufViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHuruf: TextView = itemView.findViewById(R.id.tvHuruf)
        val ivSound: ImageView = itemView.findViewById(R.id.ivSound)

        fun bind(huruf: Huruf) {
            tvHuruf.text = huruf.huruf.toString() // Menampilkan huruf atau suku kata

            ivSound.setOnClickListener {
                val textToSpeech = huruf.suara

                // Memanggil API untuk mendapatkan suara
                TtsRetrofitClient.ttsApiService.getTtsAudio(TtsRequest(textToSpeech))
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                // Menulis audio ke file sementara
                                val audioInputStream = response.body()?.byteStream()
                                if (audioInputStream != null) {
                                    try {
                                        // Menulis audio ke file sementara
                                        val tempFile = createTempFile()
                                        writeInputStreamToFile(audioInputStream, tempFile)

                                        // Memutar file sementara menggunakan MediaPlayer
                                        val mediaPlayer = MediaPlayer()
                                        mediaPlayer.setDataSource(tempFile.path)
                                        mediaPlayer.prepare()
                                        mediaPlayer.start()
                                    } catch (e: Exception) {
                                        Toast.makeText(itemView.context, "Gagal memutar audio: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(itemView.context, "Gagal memuat suara", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(itemView.context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    })
            }

            itemView.setOnClickListener { onItemClick(huruf) }
        }

        private fun createTempFile(): File {
            // Membuat file sementara di direktori cache aplikasi
            val tempDir = itemView.context.cacheDir
            return File.createTempFile("audio_", ".mp3", tempDir)
        }

        private fun writeInputStreamToFile(inputStream: InputStream, file: File) {
            try {
                val outputStream = FileOutputStream(file)
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HurufViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_huruf, parent, false)
        return HurufViewHolder(view)
    }

    override fun onBindViewHolder(holder: HurufViewHolder, position: Int) {
        holder.bind(hurufList[position])
    }

    override fun getItemCount() = hurufList.size
}