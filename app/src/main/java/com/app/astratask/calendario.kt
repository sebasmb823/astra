package com.app.astratask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.app.AlertDialog
import android.content.Context
import android.widget.EditText



class calendario : AppCompatActivity() {
    private val dateList = mutableListOf<Pair<String, String>>()
    private val adapter = DateListAdapter(dateList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)
        val selectDateButton: Button = findViewById(R.id.selectDateButton)
        val layoutManager = LinearLayoutManager(this)
        val adapter = DateListAdapter(dateList)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


        selectDateButton.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                showTitleDialog(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showTitleDialog(selectedDate: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ingrese un título para la fecha: $selectedDate")

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("Aceptar") { _, _ ->
            val title = input.text.toString()
            if (title.isNotEmpty()) {
                // Agregar la fecha y el título a la lista
                dateList.add(Pair(title, selectedDate))
                adapter.notifyDataSetChanged() // Actualizar el adaptador
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    inner class DateListAdapter(private val dateList: List<Pair<String, String>>) : RecyclerView.Adapter<DateListAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_date, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val (title, date) = dateList[position]
            holder.bind(title, date)
        }

        override fun getItemCount(): Int {
            return dateList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)

            fun bind(title: String, date: String) {
                titleTextView.text = title
                dateTextView.text = date
            }
        }
    }
}




