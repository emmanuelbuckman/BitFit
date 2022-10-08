package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val days = mutableListOf<DAY>()
    private lateinit var bitRv: RecyclerView

    private lateinit var submitButton : Button
    private lateinit var dateEv: EditText
    private lateinit var hoursEv: EditText
    private lateinit var commentsEv: EditText
    private lateinit var ratingEv: EditText
    private lateinit var subButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bitRv = findViewById<RecyclerView>(R.id.sleepRv)

        val bitAdapter = BitFitAdapter(this, days)

        bitRv.adapter = bitAdapter
        bitRv.layoutManager = LinearLayoutManager(this)

        dateEv = findViewById(R.id.dateInput)
        hoursEv = findViewById(R.id.hoursInput)
        commentsEv = findViewById(R.id.commentsInput)
        ratingEv = findViewById(R.id.ratingInput)


        lifecycleScope.launch{
            (application as BitFitApplication).db.dayDao().getAll().collect{ databaseList ->
                databaseList.map{ entity ->
                    DAY(
                        entity.date,
                        entity.hours,
                        entity.comments,
                        entity.rating
                    )
                }.also{mappedList ->
                    days.clear()
                    days.addAll(mappedList)
                    bitAdapter.notifyDataSetChanged()
                }

            }
        }

        val day = intent.getSerializableExtra("extra_entry")
        Log.i("Day test", day.toString())

        /*
        submitButton = findViewById(R.id.addNewDateButton)
        submitButton.setOnClickListener{
            val intent = Intent(this, EntryActivity::class.java)
            this.startActivity(intent)
        }
        */
        submitButton = findViewById(R.id.submitButton)
        submitButton.setOnClickListener{
            Log.i("submit clicked", "submit clicked")
            val date1 = dateEv.text.toString()
            val hours1 = hoursEv.text.toString().toInt()
            val comments1 = commentsEv.text.toString()
            val rating1 = ratingEv.text.toString().toInt()
            val day = DAY(date1, hours1, comments1, rating1)
            lifecycleScope.launch(Dispatchers.IO) {
                (application as BitFitApplication).db.dayDao().insert(
                    DayEntity(
                        date = day.date,
                        hours = day.hours,
                        comments = day.comments,
                        rating = day.rating
                    )
                )
            }

        }
    }
}