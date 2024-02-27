package com.example.studyplan1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChoosePlan : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var selectedPlan: String
    private lateinit var studyPlans: ArrayList<String>
    private lateinit var checkedCheckboxes: ArrayList<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_plan)

        auth = FirebaseAuth.getInstance()

        val back = findViewById<Button>(R.id.back2)
        back.setOnClickListener() {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val database: DatabaseReference = Firebase.database.reference
        val studyPlansRef = database.child("studyplans")

        studyPlans = ArrayList()
        checkedCheckboxes = ArrayList()

        studyPlansRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                studyPlans.clear()
                //get all plan names and set them in the arraylist
                for (planSnapshot in dataSnapshot.children) {
                    val planName = planSnapshot.key
                    planName?.let {
                        studyPlans.add(it)
                    }
                }

                val spinner = findViewById<Spinner>(R.id.spinner)
                val adapter = ArrayAdapter(this@ChoosePlan, android.R.layout.simple_spinner_item, studyPlans)
                spinner.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPlan = studyPlans[position] // here when the user select the plan
                displayCheckboxes(selectedPlan)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val saveButton = findViewById<Button>(R.id.savebutton)
        saveButton.setOnClickListener {
            val currentUser = auth.currentUser
            val userId = currentUser?.uid

            if (userId != null) {
                saveSelectedPlan(selectedPlan, userId)
                saveCheckedCheckboxes(selectedPlan, userId)
                Toast.makeText(baseContext, "Plan Saved", Toast.LENGTH_SHORT).show()
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun displayCheckboxes(selectedPlan: String) {
        val database: DatabaseReference = Firebase.database.reference
        val childStudyPlansRef = database.child("studyplans").child(selectedPlan)

        childStudyPlansRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val checkBoxContainer = findViewById<LinearLayout>(R.id.checkboxContainer)

                checkBoxContainer.removeAllViews()
                // the datasnapshot is the study plan that chosen .child(selectedPlan)
                for (childSnapshot in dataSnapshot.children) {
                    val childName = childSnapshot.child("course_name").getValue(String::class.java) // get the name from course_naem and pre_requisite
                    if (childName != null) {
                        val checkBox = CheckBox(this@ChoosePlan)
                        checkBox.text = "Course name: $childName"
                        checkBoxContainer.addView(checkBox)

                        checkBox.setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                checkedCheckboxes.add(childName)
                            } else {
                                checkedCheckboxes.remove(childName)
                            }
                        }
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun saveSelectedPlan(selectedPlan: String, userId: String) {
        val database: DatabaseReference = Firebase.database.reference
        val selectedPlanRef = database.child("user_plans").child(userId).child("selected_plan")
        selectedPlanRef.setValue(selectedPlan)
    }


    private fun saveCheckedCheckboxes(selectedPlan: String, userId: String) {
        val database: DatabaseReference = Firebase.database.reference
        val checkedCheckboxesRef = database.child("user_plans").child(userId).child("finished_subjects")
        checkedCheckboxesRef.setValue(checkedCheckboxes)
    }
}
