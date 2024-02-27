package com.example.studyplan1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AdminUse : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_use)

        val plansButton = findViewById<ImageButton>(R.id.addplans)
        plansButton.setOnClickListener(){
            intent = Intent(this, StudyPlans::class.java)
            startActivity(intent)
        }

        val ViewNum = findViewById<ImageButton>(R.id.viewnum)
        ViewNum.setOnClickListener(){
            intent = Intent(this, NumberOfStudents::class.java)
            startActivity(intent)
        }

        val loguotButton = findViewById<Button>(R.id.logout1)
        loguotButton.setOnClickListener{
            Firebase.auth.signOut()
            intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }

    }
}