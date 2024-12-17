package com.studay.app.api

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.studay.app.database.StudayDatabase
import com.studay.app.database.UserEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://studay-api-server-142018660774.asia-southeast2.run.app/studay/"

    private var retrofit: Retrofit? = null

    fun getInstance(context: Context): Retrofit {
        if (retrofit == null) {
            // Mengambil token dari SharedPreferences
            val token = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
                .getString("token", null)

            // Membuat OkHttpClient dengan AuthInterceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(token))  // Menambahkan AuthInterceptor
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit!!
    }


    // Function to insert user data into database
    fun saveUserToDatabase(context: Context, email: String, password: String) {
        val db = Room.databaseBuilder(context, StudayDatabase::class.java, "user_database").build()
        val userDao = db.userDao()

        val userEntity = UserEntity(
            email = email,
            name = "Unknown",
            password = password,
            token = ""
        )

        // Jangan gunakan GlobalScope
        (context as? AppCompatActivity)?.lifecycleScope?.launch {
            userDao.insert(userEntity)
        }
    }

}
