package com.example.flightbooking

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cities = resources.getStringArray(R.array.cities)

        val cityAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cities
        )
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo)

        spinnerFrom.adapter = cityAdapter
        spinnerTo.adapter = cityAdapter
        spinnerTo.setSelection(1)

        val departure = findViewById<TextView>(R.id.txtDeparture)
        val arrival = findViewById<TextView>(R.id.txtArrival)
        val layoutDeparture = findViewById<LinearLayout>(R.id.layoutDeparture)
        val layoutArrival = findViewById<LinearLayout>(R.id.layoutArrival)

        layoutDeparture.setOnClickListener { showDatePicker(departure) }
        layoutArrival.setOnClickListener { showDatePicker(arrival) }

        val etAdults = findViewById<EditText>(R.id.etAdults)
        val etChildren = findViewById<EditText>(R.id.etChildren)
        val etInfants = findViewById<EditText>(R.id.etInfants)

        findViewById<Button>(R.id.btnSearch).setOnClickListener {
            val from = spinnerFrom.selectedItem.toString()
            val to = spinnerTo.selectedItem.toString()

            when {
                from == to -> {
                    Toast.makeText(this, "Выберите разные города", Toast.LENGTH_SHORT).show()
                }
                departure.text == "Выбрать дату" || arrival.text == "Выбрать дату" -> {
                    Toast.makeText(this, "Выберите даты поездки", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val adults = etAdults.text.toString().ifEmpty { "0" }
                    val children = etChildren.text.toString().ifEmpty { "0" }
                    val infants = etInfants.text.toString().ifEmpty { "0" }

                    Toast.makeText(
                        this,
                        "Маршрут: $from — $to\nПассажиры: $adults взр., $children дет., $infants мл.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showDatePicker(target: TextView) {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, year, month, day ->
                val date = String.format(
                    Locale.getDefault(),
                    "%02d.%02d.%04d",
                    day,
                    month + 1,
                    year
                )
                target.text = date
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}