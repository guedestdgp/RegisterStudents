package com.agenewdigital.tatiane_c0755772_testlab1.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.agenewdigital.tatiane_c0755772_testlab1.R
import com.agenewdigital.tatiane_c0755772_testlab1.viewmodel.StudentsViewModel
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()!!
    private lateinit var studentsViewModel: StudentsViewModel
    private lateinit var studentView: StudentsViewModel.StudentDetailsView
    private var studentId: Long? = null
    private var deleteButton = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        title = getString(R.string.title_student)

        setCalendar()
        setupViewModel()
        getIntentData()
    }

    private fun getIntentData() {
        val studentId = intent.getStringExtra("studentId")
        if (studentId != "new") {
            deleteButton = true
            studentsViewModel.getStudent(studentId.toLong())?.observe(
                this, Observer {
                    it?.let {
                        studentView = it
                        setFields()
                    }
                })
        }
    }

    private fun setFields() {
        studentView?.let { studentView ->
            et_name.setText(studentView.name)
            et_age.setText(studentView.age.toString())
            et_tuition.setText(String.format("%.2f", studentView.tuition))

            val formatDate = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            cal.time = studentView.date
            et_date.setText(formatDate.format(studentView.date))
            studentId = studentView.id!!
        }
    }

    private fun setCalendar() {
        //Configuring the calendar
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        //Calling the calendar when touch edittext of the date
        et_date!!.setOnClickListener {
            hideKeyboard()
            DatePickerDialog(
                this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_register, menu)
        menu.findItem(R.id.action_delete).isVisible = deleteButton
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                saveClick()
                true
            }
            R.id.action_delete -> {
                deleteClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        et_date!!.setText(sdf.format(this.cal.time))
    }

    //Method to hide the keyboard
    private fun hideKeyboard() {
        var inputManager:InputMethodManager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.SHOW_FORCED)
    }

    private fun setupViewModel() {
        studentsViewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)
    }

    private fun saveClick() {

        if (checkFields()) return
        hideKeyboard()

        if (studentId == null) {
            studentsViewModel.addNewStudent(
                et_name.text.toString(),
                et_age.text.toString().toInt(),
                et_tuition.text.toString().toDouble(),
                cal.time as Date
            )
        } else {
            studentView.name = et_name.text.toString()
            studentView.age = et_age.text.toString().toInt()
            studentView.tuition = et_tuition.text.toString().toDouble()
            studentView.date = cal.time as Date
            studentsViewModel.updateStudent(studentView)
        }

        finish()
    }

    private fun deleteClick() {
//        hideKeyboard()

        AlertDialog.Builder(this) .setMessage("Delete?")
            .setPositiveButton("Ok") { _, _ ->
                studentsViewModel.deleteStudent(studentView)
            finish()
            }
            .setNegativeButton("Cancel", null) .create().show()
    }

    private fun checkFields(): Boolean {
        val name = et_name.text.toString()
        val age = et_age.text.toString()
        val tuition = et_tuition.text.toString()
        val date = et_date.text.toString()
        when ("") {
            name -> {
                showDialog("name")
                return true
            }
            age -> {
                showDialog("age")
                return true
            }
            tuition -> {
                showDialog("tuition")
                return true
            }
            date -> {
                showDialog("start date term")
                return true
            }
        }
        return false
    }

    private fun showDialog(field:String) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Check")
        builder.setMessage("Blank: "+field)

        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss() }

        builder.create().show()
    }
}

