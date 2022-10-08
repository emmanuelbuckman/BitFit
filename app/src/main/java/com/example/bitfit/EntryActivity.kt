package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
/*
class EntryActivity : AppCompatActivity() {

    private lateinit var dateEv: EditText
    private lateinit var hoursEv: EditText
    private lateinit var commentsEv: EditText
    private lateinit var ratingEv: EditText
    private lateinit var subButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        dateEv = findViewById(R.id.dateInput)
        hoursEv = findViewById(R.id.hoursInput)
        commentsEv = findViewById(R.id.commentsInput)
        ratingEv = findViewById(R.id.ratingInput)
        subButton = findViewById(R.id.submitButton)

        subButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val day = DAY(dateEv.text.toString(), hoursEv.text.toString().toInt(), commentsEv.text.toString(), ratingEv.text.toString().toInt())
            intent.putExtra("extra_entry", day)
            this.startActivity(intent)
        }
    }
}

     */