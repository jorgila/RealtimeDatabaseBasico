package com.estholon.realtimedatabasebasico.data

import android.content.Context
import com.estholon.realtimedatabasebasico.domain.entities.Todo
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class FirebaseInstance(context: Context) {

    private val database = Firebase.database("https://realtime-database-30f6f-default-rtdb.europe-west1.firebasedatabase.app")

    private val myRef = database.reference

    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase(){

        val randomValue: String = Random.nextInt(1,200).toString()

        val newItem = myRef.push()
        newItem.setValue(getGenericTodoTasksItem(randomValue))

    }

    fun setUpDatabaseListener(postListener: ValueEventListener) {
        database.reference.addValueEventListener(postListener)
    }

    private fun getGenericTodoTasksItem(randomValue: String) =
        Todo(title = "Tarea $randomValue", description = "Esto es una descripción cremita")

    fun removeFromDatabase(reference: String) {
        myRef.child(reference).removeValue()
    }
}