package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val days = mutableListOf<DAY>()
    private lateinit var bitRv: RecyclerView

    private lateinit var submitButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bitRv = findViewById<RecyclerView>(R.id.sleepRv)

        val bitAdapter = BitFitAdapter(this, days)

        bitRv.adapter = bitAdapter
        bitRv.layoutManager = LinearLayoutManager(this)

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

        if (day != null){
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
        else{
            Log.d("listAct", "no extra" )
        }

        submitButton = findViewById(R.id.addNewDateButton)
        submitButton.setOnClickListener{
            val intent = Intent(this, EntryActivity::class.java)
            this.startActivity(intent)
        }
    }
}