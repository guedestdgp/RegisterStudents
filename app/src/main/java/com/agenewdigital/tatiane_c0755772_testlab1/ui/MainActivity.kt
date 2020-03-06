package com.agenewdigital.tatiane_c0755772_testlab1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agenewdigital.tatiane_c0755772_testlab1.R
import com.agenewdigital.tatiane_c0755772_testlab1.adapter.StudentsRecyclerViewAdapter
import com.agenewdigital.tatiane_c0755772_testlab1.viewmodel.StudentsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var listsRecyclerView: RecyclerView
    private lateinit var studentsViewModel: StudentsViewModel

    companion object {
        const val NEW_STUDENT = "new"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        listsRecyclerView = findViewById(R.id.students_recyclerview)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)
        listsRecyclerView.adapter = StudentsRecyclerViewAdapter { registerStudent : String -> goToRegisterActivity(registerStudent) }

        setupViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                goToRegisterActivity(NEW_STUDENT)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToRegisterActivity(registerStudent: String?) {
        val intent = Intent(this, RegisterActivity::class.java)
        registerStudent.let { intent.putExtra("studentId", it) }
        startActivity(intent)

    }

    private fun setupViewModel() {
        studentsViewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)

        studentsViewModel.getAllStudents().observe(this, Observer { students ->
            // Update the cached copy of the words in the adapter.
            students?.let { (listsRecyclerView.adapter as StudentsRecyclerViewAdapter).setStudents(it) }
        })
    }
}
