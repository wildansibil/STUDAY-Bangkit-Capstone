package com.studay.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class ActivityBridgeMenulis : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge_menulis)

        // Menginisialisasi CardView
        val cvMenulisAbjad: CardView = findViewById(R.id.cvMenulisAbjad)
        val cvTesMenulis: CardView = findViewById(R.id.cvTesMenulis)

        // Set listener untuk CardView Menulis Abjad
        cvMenulisAbjad.setOnClickListener {
            val intent = Intent(this, MenulisActivity::class.java)
            startActivity(intent)
        }

        // Set listener untuk CardView Tes Menulis
        cvTesMenulis.setOnClickListener {
            val intent = Intent(this, MenulisActivity2::class.java)
            startActivity(intent)
        }
    }
}
