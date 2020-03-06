package com.agenewdigital.tatiane_c0755772_testlab1.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.agenewdigital.tatiane_c0755772_testlab1.R
import com.agenewdigital.tatiane_c0755772_testlab1.model.Student
import java.text.SimpleDateFormat
import java.util.*

class StudentsRecyclerViewAdapter (val clickListener: (registerStudent:String) -> Unit) :
    RecyclerView.Adapter<StudentsRecyclerViewAdapter.StudentViewHolder>() {

    private var studentsData = emptyList<Student>()

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listName: TextView = itemView.findViewById(R.id.tx_name)
        val listAge: TextView = itemView.findViewById(R.id.tx_age)
        val listTuition: TextView = itemView.findViewById(R.id.tx_tuition)
        val listDate: TextView = itemView.findViewById(R.id.tx_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_view_holder, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount() = studentsData.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.listName.text =  studentsData[position].name
        holder.listAge.text = "Age: " + studentsData[position].age.toString()
        holder.listTuition.text = "Tuition: $"+ String.format("%.2f", studentsData[position].tuition)

        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        holder.listDate.text = "Start Date Term: " + sdf.format(studentsData[position].date)

        //Long click delete and one click go to edit page
        holder.itemView.setOnClickListener {
            clickListener(studentsData[position].id.toString())
        }
    }

    internal fun setStudents(students: List<Student>) {
        this.studentsData = students
        notifyDataSetChanged()
    }
}