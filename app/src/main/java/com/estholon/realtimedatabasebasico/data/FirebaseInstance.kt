package com.estholon.realtimedatabasebasico.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class FirebaseInstance(context: Context) {

    private val database = Firebase.database("https://realtime-database-30f6f-default-rtdb.europe-west1.firebasedatabase.app")

    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase(){
        val myRef = database.reference
        val randomValue: String = Random.nextInt(1,200).toString()
        myRef.setValue("Mi primera escritura: $randomValue")
    }

    fun setUpDatabaseListener(postListener: ValueEventListener) {
        database.reference.addValueEventListener(postListener)
    }

}