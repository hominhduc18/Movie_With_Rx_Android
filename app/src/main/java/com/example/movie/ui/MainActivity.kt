package com.example.movie.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.movie.R
import com.example.movie.ui.single_movie_detail.SingleMovie

class MainActivity : AppCompatActivity() {
    private lateinit var btn_main: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_main = findViewById(R.id.btn_main)

        btn_main.setOnClickListener {
            val intent = Intent(this, SingleMovie::class.java)
            intent.putExtra("id", 299634)
            this.startActivity(intent)
        }
    }
}
