package com.example.studyplan1

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class StudyPlans : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_plans)

        val back = findViewById<Button>(R.id.back3)
        back.setOnClickListener(){
            intent = Intent(this, AdminUse::class.java)
            startActivity(intent)
        }

        val save = findViewById<Button>(R.id.save)
        val planName = findViewById<EditText>(R.id.planname)

        val id1 = findViewById<EditText>(R.id.id1)
        val id2 = findViewById<EditText>(R.id.id2)
        val id3 = findViewById<EditText>(R.id.id3)
        val id4 = findViewById<EditText>(R.id.id4)
        val id5 = findViewById<EditText>(R.id.id5)
        val id6 = findViewById<EditText>(R.id.id6)
        val id7 = findViewById<EditText>(R.id.id7)
        val id8 = findViewById<EditText>(R.id.id8)
        val id9 = findViewById<EditText>(R.id.id9)
        val id10 = findViewById<EditText>(R.id.id10)
        val id11 = findViewById<EditText>(R.id.id11)
        val id12 = findViewById<EditText>(R.id.id12)
        val id13 = findViewById<EditText>(R.id.id13)
        val id14 = findViewById<EditText>(R.id.id14)
        val id15 = findViewById<EditText>(R.id.id15)
        val id16 = findViewById<EditText>(R.id.id16)

        val name1 = findViewById<EditText>(R.id.name1)
        val name2 = findViewById<EditText>(R.id.name2)
        val name3 = findViewById<EditText>(R.id.name3)
        val name4 = findViewById<EditText>(R.id.name4)
        val name5 = findViewById<EditText>(R.id.name5)
        val name6 = findViewById<EditText>(R.id.name6)
        val name7 = findViewById<EditText>(R.id.name7)
        val name8 = findViewById<EditText>(R.id.name8)
        val name9 = findViewById<EditText>(R.id.name9)
        val name10 = findViewById<EditText>(R.id.name10)
        val name11 = findViewById<EditText>(R.id.name11)
        val name12 = findViewById<EditText>(R.id.name12)
        val name13 = findViewById<EditText>(R.id.name13)
        val name14 = findViewById<EditText>(R.id.name14)
        val name15 = findViewById<EditText>(R.id.name15)
        val name16 = findViewById<EditText>(R.id.name16)

        val pre1 = findViewById<EditText>(R.id.pre1)
        val pre2 = findViewById<EditText>(R.id.pre1)
        val pre3 = findViewById<EditText>(R.id.pre3)
        val pre4 = findViewById<EditText>(R.id.pre4)
        val pre5 = findViewById<EditText>(R.id.pre5)
        val pre6 = findViewById<EditText>(R.id.pre6)
        val pre7 = findViewById<EditText>(R.id.pre7)
        val pre8 = findViewById<EditText>(R.id.pre8)
        val pre9 = findViewById<EditText>(R.id.pre9)
        val pre10 = findViewById<EditText>(R.id.pre10)
        val pre11 = findViewById<EditText>(R.id.pre11)
        val pre12 = findViewById<EditText>(R.id.pre12)
        val pre13 = findViewById<EditText>(R.id.pre13)
        val pre14 = findViewById<EditText>(R.id.pre14)
        val pre15 = findViewById<EditText>(R.id.pre15)
        val pre16 = findViewById<EditText>(R.id.pre16)


        val database: DatabaseReference = Firebase.database.reference

        save.setOnClickListener {
            val course = planName.text.toString()
            val Name1 = name1.text.toString()
            val Name2 = name2.text.toString()
            val Name3 = name2.text.toString()
            val Name4 = name4.text.toString()
            val Name5 = name5.text.toString()
            val Name6 = name6.text.toString()
            val Name7 = name7.text.toString()
            val Name8 = name8.text.toString()
            val Name9 = name9.text.toString()
            val Name10 = name10.text.toString()
            val Name11 = name11.text.toString()
            val Name12 = name12.text.toString()
            val Name13 = name13.text.toString()
            val Name14 = name14.text.toString()
            val Name15 = name15.text.toString()
            val Name16 = name16.text.toString()

            val Id1 = id1.text.toString()
            val Id2 = id2.text.toString()
            val Id3 = id3.text.toString()
            val Id4 = id4.text.toString()
            val Id5 = id5.text.toString()
            val Id6 = id6.text.toString()
            val Id7 = id7.text.toString()
            val Id8 = id8.text.toString()
            val Id9 = id9.text.toString()
            val Id10 = id10.text.toString()
            val Id11 = id11.text.toString()
            val Id12 = id12.text.toString()
            val Id13 = id13.text.toString()
            val Id14 = id14.text.toString()
            val Id15 = id15.text.toString()
            val Id16 = id16.text.toString()

            val pre1 = pre1.text.toString()
            val pre2 = pre2.text.toString()
            val pre3 = pre3.text.toString()
            val pre4 = pre4.text.toString()
            val pre5 = pre5.text.toString()
            val pre6 = pre6.text.toString()
            val pre7 = pre7.text.toString()
            val pre8 = pre8.text.toString()
            val pre9 = pre9.text.toString()
            val pre10 = pre10.text.toString()
            val pre11 = pre11.text.toString()
            val pre12 = pre12.text.toString()
            val pre13 = pre13.text.toString()
            val pre14 = pre14.text.toString()
            val pre15 = pre15.text.toString()
            val pre16 = pre16.text.toString()


            database.child("studyplans").child(course).child(Id1).setValue(data(Name1,pre1))
            database.child("studyplans").child(course).child(Id2).setValue(data(Name2,pre2))
            database.child("studyplans").child(course).child(Id3).setValue(data(Name3,pre3))
            database.child("studyplans").child(course).child(Id4).setValue(data(Name4,pre4))
            database.child("studyplans").child(course).child(Id5).setValue(data(Name5,pre5))
            database.child("studyplans").child(course).child(Id6).setValue(data(Name6,pre6))
            database.child("studyplans").child(course).child(Id7).setValue(data(Name7,pre7))
            database.child("studyplans").child(course).child(Id8).setValue(data(Name8,pre8))
            database.child("studyplans").child(course).child(Id9).setValue(data(Name9,pre9))
            database.child("studyplans").child(course).child(Id10).setValue(data(Name10,pre10))
            database.child("studyplans").child(course).child(Id11).setValue(data(Name11,pre11))
            database.child("studyplans").child(course).child(Id12).setValue(data(Name12,pre12))
            database.child("studyplans").child(course).child(Id13).setValue(data(Name13,pre13))
            database.child("studyplans").child(course).child(Id14).setValue(data(Name14,pre14))
            database.child("studyplans").child(course).child(Id15).setValue(data(Name15,pre15))
            database.child("studyplans").child(course).child(Id16).setValue(data(Name16,pre16))

            Toast.makeText(baseContext, "Study Plan created", Toast.LENGTH_SHORT,).show()
            intent = Intent(this, AdminUse::class.java)
            startActivity(intent)

        }

    }

}