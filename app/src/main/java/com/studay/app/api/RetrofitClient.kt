package com.studay.app.api

import android.content.Context
import androidx.room.Room
import com.studay.app.database.StudayDatabase
import com.studay.app.database.UserEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://studay-api-server-142018660774.asia-southeast2.run.app/studay/"

    private var retrofit: Retrofit? = null

    // Retrofit Instance
    fun getInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    // Function to insert user data into database
    fun saveUserToDatabase(context: Context, email: String, password: String) {
        // Initialize the database and DAO
        val db = Room.databaseBuilder(context, StudayDatabase::class.java, "user_database").build()
        val userDao = db.userDao()

        // Prepare the user entity to insert
        val userEntity = UserEntity(
            id = "unique_user_id",  // Use a proper user ID
            email = email,
            name = "Unknown",  // Replace with actual user data
            password = password,  // Ensure this password is hashed/encrypted
            token = "" // Use actual token data
        )

        // Insert user entity into the database
        GlobalScope.launch {
            userDao.insert(userEntity)
        }
    }
}
