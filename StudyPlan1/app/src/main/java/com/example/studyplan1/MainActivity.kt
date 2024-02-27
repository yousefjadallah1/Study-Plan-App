package com.example.studyplan1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var loguotButton: Button
    lateinit var user: FirebaseUser
    lateinit var usernamee: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loguotButton = findViewById(R.id.logout)
        usernamee = findViewById(R.id.Username) // Initialize the usernamee TextView

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        if (user == null) {
            intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        } else {
            usernamee.text = user.email.toString() // Set the email of the current user
        }

        loguotButton.setOnClickListener{
            Firebase.auth.signOut()
            intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }

        val choosePlan = findViewById<ImageView >(R.id.chooseplan)
        choosePlan.setOnClickListener(){
            intent = Intent(this, ChoosePlan::class.java)
            startActivity(intent)
        }
        val sug = findViewById<ImageView >(R.id.suggested)
        sug.setOnClickListener(){
            intent = Intent(this, SuggestedCourses::class.java)
            startActivity(intent)
        }
        val ver = findViewById<ImageView >(R.id.versustrans)
        ver.setOnClickListener(){
            intent = Intent(this, Transcript::class.java)
            startActivity(intent)
        }







    }
}
