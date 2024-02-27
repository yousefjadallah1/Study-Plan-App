package com.example.studyplan1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SuggestedCourses : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggested_courses)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference

        val backButton = findViewById<Button>(R.id.back1)
        backButton.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val userId = auth.currentUser?.uid
        if (userId != null) {
            fetchSuggestedCourses(userId)
        }
    }

    private fun fetchSuggestedCourses(userId: String) {
        val userPlansRef = database.child("user_plans").child(userId) // here snapshot in the userID views finished and studyplan

        userPlansRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(userPlansSnapshot: DataSnapshot) {
                if (userPlansSnapshot.child("selected_plan").exists()) {
                    val selectedPlan = userPlansSnapshot.child("selected_plan").getValue(String::class.java) // plan name in this

                    selectedPlan?.let { planId ->
                        val studyPlanRef = database.child("studyplans").child(planId) // here under the course IDs

                        studyPlanRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(studyPlanSnapshot: DataSnapshot) {
                                val finishedSubjectsRef = database.child("user_plans").child(userId).child("finished_subjects")

                                finishedSubjectsRef.addListenerForSingleValueEvent(object : ValueEventListener { // here under finished
                                    override fun onDataChange(finishedSnapshot: DataSnapshot) {
                                        val suggestedCoursesLayout = findViewById<LinearLayout>(R.id.suggestedCoursesLayout)
                                        suggestedCoursesLayout.removeAllViews()

                                        val finishedSubjects = finishedSnapshot.children.mapNotNull { it.getValue(String::class.java) }
                                        val courseIds = studyPlanSnapshot.children.mapNotNull { it.key }

                                        GlobalScope.launch(Dispatchers.Main) {
                                            var suggestedCount = 0

                                            for (subjectSnapshot in studyPlanSnapshot.children) {
                                                val courseId = subjectSnapshot.key
                                                val courseName = subjectSnapshot.child("course_name").getValue(String::class.java)
                                                val preRequisite = subjectSnapshot.child("pre_requisite").getValue(String::class.java)

                                                if (courseName in finishedSubjects) { // here to not view it
                                                    continue
                                                }

                                                if (preRequisite == "0" || preRequisite in courseIds) {
                                                    if (suggestedCount >= 6) {
                                                        break
                                                    }

                                                    val suggestedCourseView = layoutInflater.inflate(R.layout.suggested_course_item, suggestedCoursesLayout, false)
                                                    val courseIdTextView = suggestedCourseView.findViewById<TextView>(R.id.courseIdTextView)
                                                    val courseNameTextView = suggestedCourseView.findViewById<TextView>(R.id.courseNameTextView)

                                                    courseIdTextView.text = "Course ID: $courseId"
                                                    courseNameTextView.text = "Course Name: $courseName"

                                                    if (preRequisite == "0") {
                                                        courseNameTextView.setTextColor(Color.parseColor("#FF5722"))
                                                    } else {
                                                        courseNameTextView.setTextColor(Color.WHITE)
                                                    }

                                                    suggestedCoursesLayout.addView(suggestedCourseView)
                                                    suggestedCount++
                                                }
                                            }
                                        }
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {
                                        // Handle error
                                    }
                                })
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Handle error
                            }
                        })
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}
