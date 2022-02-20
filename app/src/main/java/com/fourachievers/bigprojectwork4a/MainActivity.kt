package com.fourachievers.bigprojectwork4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN ,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(currentUser.phoneNumber.toString()).get().addOnSuccessListener {
                if (it.child("emailId").value.toString() != "null") {
                    Timer("logging In" , false).schedule(1000) {
                        val intent = Intent(this@MainActivity , Dashboard::class.java)
                        startActivity(intent)
                        this@MainActivity.finish()
                    }

                } else {
                    Timer("SettingUp" , false).schedule(1000) {
                        val i = Intent(this@MainActivity , Login::class.java)
                        startActivity(i)
                        this@MainActivity.finish()
                    }

                }
            }
        } else {
            Timer("SettingUp" , false).schedule(1000) {
                val i = Intent(this@MainActivity , Login::class.java)
                startActivity(i)
                this@MainActivity.finish()
            }
        }
    }
}