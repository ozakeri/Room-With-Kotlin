package com.example.kotlinroomviewmodel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import kotlinx.android.synthetic.main.activity_add_edit.*

class AddOrUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "EXTRA_PRIORITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        val editTextTitle = findViewById<EditText>(R.id.editTextTitle)
        val editTextDesc = findViewById<EditText>(R.id.editTextDesc)
        val numberPicker = findViewById<NumberPicker>(R.id.numberPicker)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Update Note")
            var s: String = intent.getStringExtra(EXTRA_TITLE)
            var a: Int = s.toInt()
            editTextTitle.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            editTextDesc.setText(intent.getStringExtra(EXTRA_PRIORITY))
            numberPicker.value = a
        } else {
            setTitle("Add Note")
        }


        numberPicker.minValue = 1
        numberPicker.maxValue = 10
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.addNote -> {
                saveNote()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun saveNote() {
        var strTitle: String = editTextTitle.text.toString()
        var strDescription: String = editTextTitle.text.toString()
        var priority: Int = numberPicker.value

        if (strTitle.isEmpty()) {
            editTextTitle.setError("Title Requred")
            editTextTitle.requestFocus()
        }

        if (strDescription.isEmpty()) {
            editTextDesc.setError("Description Requred")
            editTextDesc.requestFocus()
        }

        intent.putExtra(EXTRA_TITLE, strTitle)
        intent.putExtra(EXTRA_DESCRIPTION, strDescription)
        intent.putExtra(EXTRA_PRIORITY, priority)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}