package com.example.studyplan1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NumberOfStudents : AppCompatActivity() {
    private lateinit var plans: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_of_students)

        val back = findViewById<Button>(R.id.back4)
        back.setOnClickListener(){
            intent = Intent(this, AdminUse::class.java)
            startActivity(intent)
        }

        val text = findViewById<TextView>(R.id.textview11)
        val spinner = findViewById<Spinner>(R.id.spinner)

        plans = ArrayList()

        val database: DatabaseReference = Firebase.database.reference
        val studyPlansRef = database.child("studyplans")

        studyPlansRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                plans.clear()

                for (planSnapshot in dataSnapshot.children) {
                    val planName = planSnapshot.key
                    planName?.let {
                        plans.add(it)
                    }
                }

                //view in thw spinner the study plans
                val adapter = ArrayAdapter(this@NumberOfStudents, android.R.layout.simple_spinner_item, plans)
                spinner.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedPlan = plans[position]
                text.text = "Number of students in $selectedPlan: "
                fetchNumberOfStudents(selectedPlan)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun fetchNumberOfStudents(planName: String) {
        val database: DatabaseReference = Firebase.database.reference
        val userPlansRef = database.child("user_plans")

        userPlansRef.orderByChild("selected_plan").equalTo(planName).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val numberOfStudents = dataSnapshot.childrenCount
                val text = findViewById<TextView>(R.id.textview11)
                text.text = "Number of students in $planName: $numberOfStudents"
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

}





