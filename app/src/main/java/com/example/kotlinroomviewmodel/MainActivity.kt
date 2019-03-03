package com.example.kotlinroomviewmodel

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.kotlinroomviewmodel.model.Note
import com.example.kotlinroomviewmodel.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    private val ADD_REQUEST = 1
    private val DELETE_REQUEST = 2
    private lateinit var noteViewMode: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteViewMode = ViewModelProviders.of(this).get(NoteViewModel::class.java)


        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floating_button_add)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = NoteAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)

        floatingActionButton.setOnClickListener {
            startActivityForResult(Intent(this, AddOrUpdateActivity::class.java), ADD_REQUEST)
        }

        noteViewMode.getAllNotes().observe(this,
            Observer<List<Note>> { t ->
                adapter.setNote(t!!)

            })

        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        setRecyclerViewItemTouchListener(recyclerView, adapter)



        adapter.setOnItemClickListener(object : NoteAdapter.onItemClickListener {
            override fun onClick(view: View, note: Note) {
                Toast.makeText(applicationContext, note.id, Toast.LENGTH_LONG).show()
            }
        })
}

fun showActivity(note: Note) {
    val intent = Intent(this, AddOrUpdateActivity::class.java)
    intent.putExtra(AddOrUpdateActivity.EXTRA_ID, note.id)
    intent.putExtra(AddOrUpdateActivity.EXTRA_TITLE, note.title)
    intent.putExtra(AddOrUpdateActivity.EXTRA_DESCRIPTION, note.description)
    intent.putExtra(AddOrUpdateActivity.EXTRA_PRIORITY, note.priority)
    startActivityForResult(intent, DELETE_REQUEST)
}

private fun setRecyclerViewItemTouchListener(recyclerView: RecyclerView, adapter: NoteAdapter) {

    //1
    val itemTouchCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                //2
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3
                val position = viewHolder.adapterPosition
                noteViewMode.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                recyclerView.adapter?.notifyItemRemoved(position)
            }
        }

    //4
    val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
    itemTouchHelper.attachToRecyclerView(recyclerView)
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == ADD_REQUEST && resultCode == Activity.RESULT_OK) {
        var strTitle: String = data!!.getStringExtra(AddOrUpdateActivity.EXTRA_TITLE)
        var strDescription: String = data.getStringExtra(AddOrUpdateActivity.EXTRA_DESCRIPTION)
        var strPriority: Int = data.getIntExtra(AddOrUpdateActivity.EXTRA_PRIORITY, 1)

        val note = Note(strTitle, strDescription, strPriority)
        noteViewMode.insert(note)
        Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()

    } else {
        Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show()
    }
}

override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
}

override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return when (item?.itemId) {
        R.id.deleteAll -> {
            deleteAll()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}

private fun deleteAll() {
    noteViewMode.deleteAllNotes()
}
}