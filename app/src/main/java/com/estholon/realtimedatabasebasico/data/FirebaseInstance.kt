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

    fun writeOnFirebase(title: String, description: String){

        val newItem = myRef.push()
        newItem.setValue(Todo(title,description))

    }

    fun setUpDatabaseListener(postListener: ValueEventListener) {
        database.reference.addValueEventListener(postListener)
    }

    fun removeFromDatabase(reference: String) {
        myRef.child(reference).removeValue()
    }

    fun updateFromDatabase(reference: String) {
        myRef.child(reference).child("done").setValue(true)
    }
}