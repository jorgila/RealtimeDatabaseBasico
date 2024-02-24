package com.estholon.realtimedatabasebasico

import android.app.Dialog
import android.app.Notification.Action
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager.LayoutParams
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.estholon.realtimedatabasebasico.data.FirebaseInstance
import com.estholon.realtimedatabasebasico.data.adapter.TodoAdapter
import com.estholon.realtimedatabasebasico.databinding.ActivityMainBinding
import com.estholon.realtimedatabasebasico.databinding.LiAddTaskBinding
import com.estholon.realtimedatabasebasico.domain.entities.Actions
import com.estholon.realtimedatabasebasico.domain.entities.Todo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    // BINDING VARIABLE
    private lateinit var binding : ActivityMainBinding

    // FIREBASE VARIABLE
    private lateinit var firebaseInstance : FirebaseInstance

    // ADAPTER
    private lateinit var todoAdapter : TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // BINDING START
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // BINDING BODY
        firebaseInstance = FirebaseInstance(this)
        initUI()
        initListeners()
    }

    private fun initUI() {

        todoAdapter = TodoAdapter{action, reference ->

            when(action){
                Actions.DELETE -> firebaseInstance.removeFromDatabase(reference)
                Actions.DONE -> firebaseInstance.updateFromDatabase(reference)
            }


        }
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnAddTask -> { showDialog() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog(){
        val binding = LiAddTaskBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(binding.root)

        dialog.window?.setLayout(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)

        binding.btnAddTask.setOnClickListener{

            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()

            if(title.isEmpty() || description.isEmpty()){
                Toast.makeText(this,"No puedes dejar los campos vacíos.",Toast.LENGTH_LONG).show()
            } else {
                firebaseInstance.writeOnFirebase(title,description)
                dialog.dismiss()
            }
        }

        dialog.show()
    }
    private fun initListeners() {
        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = getCleanSnapshot(snapshot)
                todoAdapter.setNewList(data)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("MainActivity onCancelled", error.details)
            }

        }

        firebaseInstance.setUpDatabaseListener(postListener)
    }

    private fun getCleanSnapshot(snapshot: DataSnapshot): List<Pair<String, Todo>> {
        val list = snapshot.children.map { item ->
            Pair(item.key!!, item.getValue(Todo::class.java)!!)
        }
        return list
    }

}