package com.studay.app

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class MenulisHelper(private val context: Context) {

    private lateinit var interpreter: Interpreter

    // Fungsi untuk memuat model TFLite
    fun loadModel(): Interpreter {
        val model = loadModelFile()
        interpreter = Interpreter(model)
        return interpreter
    }

    // Fungsi untuk memuat file model dari assets
    private fun loadModelFile(): ByteBuffer {
        val assetManager = context.assets
        val fileDescriptor = assetManager.openFd("model.tflite")
        val inputStream = fileDescriptor.createInputStream()
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.length
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    // Fungsi untuk memproses input gambar dan mengembalikan hasil prediksi
    fun runInference(input: ByteBuffer): ByteBuffer {
        val output = ByteBuffer.allocateDirect(4 * 10) // Sesuaikan dengan output model (misal, 10 kelas)
        output.order(ByteOrder.nativeOrder())
        // Pastikan ukuran input dan output sesuai dengan model Anda
        interpreter.run(input, output)
        return output
    }

    // Menutup interpreter setelah selesai
    fun close() {
        interpreter.close()
    }
}