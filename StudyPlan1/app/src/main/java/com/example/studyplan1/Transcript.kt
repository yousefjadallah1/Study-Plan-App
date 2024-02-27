package com.example.studyplan1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Transcript : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transcript)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        database = FirebaseDatabase.getInstance().reference

        if (userId != null) {
            fetchStudyPlan(userId)
        }

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchStudyPlan(userId: String) {
        val userPlansRef = database.child("user_plans").child(userId)

        userPlansRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(userPlansSnapshot: DataSnapshot) {
                if (userPlansSnapshot.child("selected_plan").exists()) {
                    val selectedPlan = userPlansSnapshot.child("selected_plan").getValue(String::class.java)
                    //plan name under selectedPlan
                    selectedPlan?.let { planId ->
                        val studyPlanRef = database.child("studyplans").child(planId)
                        //to get to the course Ids for the selected plan
                        studyPlanRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(studyPlanSnapshot: DataSnapshot) {
                                val subjectsLayout = findViewById<LinearLayout>(R.id.subjectsLayout)
                                subjectsLayout.removeAllViews()
                                    // key for the ID and child for the course name because its child of the cours id
                                for (subjectSnapshot in studyPlanSnapshot.children) {
                                    val courseId = subjectSnapshot.key
                                    val courseName = subjectSnapshot.child("course_name").getValue(String::class.java)

                                    val subjectView = layoutInflater.inflate(R.layout.subject_item, subjectsLayout, false)
                                    val courseIdTextView = subjectView.findViewById<TextView>(R.id.courseIdTextView)
                                    val courseNameTextView = subjectView.findViewById<TextView>(R.id.courseNameTextView)

                                    courseIdTextView.text = "Course ID: $courseId"
                                    courseNameTextView.text = "Course Name: $courseName"

                                    val finishedSubjectsRef = database.child("user_plans").child(userId).child("finished_subjects")

                                    finishedSubjectsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(finishedSnapshot: DataSnapshot) {
                                            val finishedSubjects = finishedSnapshot.children.mapNotNull { it.getValue(String::class.java) }
                                            if (finishedSubjects.contains(courseName)) { // here course name get it and compare to course name in the finished_subjects
                                                courseIdTextView.setTextColor(Color.GREEN)
                                                courseNameTextView.setTextColor(Color.GREEN)
                                            } else {
                                                courseIdTextView.setTextColor(Color.RED)
                                                courseNameTextView.setTextColor(Color.RED)
                                            }
                                        }

                                        override fun onCancelled(databaseError: DatabaseError) {
                                        }
                                    })

                                    subjectsLayout.addView(subjectView)
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Toast.makeText(baseContext, "Failed to fetch study plan", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                } else {
                    Toast.makeText(baseContext, "No study plan selected", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(baseContext, "Failed to fetch user plans", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
