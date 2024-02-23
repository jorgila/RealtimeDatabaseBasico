package com.estholon.realtimedatabasebasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.estholon.realtimedatabasebasico.data.FirebaseInstance
import com.estholon.realtimedatabasebasico.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {

    // BINDING VARIABLE
    private lateinit var binding : ActivityMainBinding

    // FIREBASE VARIABLE
    private lateinit var firebaseInstance : FirebaseInstance

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
        binding.btnUpdate.setOnClickListener{
            firebaseInstance.writeOnFirebase()
        }
    }

    private fun initListeners() {
        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value.toString()
                if(data!=null){
                    binding.tvResult.text = data
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("MainActivity onCancelled", error.details)
            }

        }

        firebaseInstance.setUpDatabaseListener(postListener)
    }
}