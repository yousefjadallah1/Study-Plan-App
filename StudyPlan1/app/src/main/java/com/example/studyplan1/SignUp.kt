package com.example.studyplan1

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUp : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var progressbar: ProgressBar
    lateinit var radioGroup: RadioGroup
    lateinit var radioButtonAdmin: RadioButton
    lateinit var radioButtonNotAdmin: RadioButton

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        val emaill: TextInputEditText = findViewById(R.id.emailsignUp)
        val pass: TextInputEditText = findViewById(R.id.passwordsignUp)
        val signUP: Button = findViewById(R.id.signup)
        progressbar = findViewById(R.id.progressBar)
        val clickToSignIn = findViewById<TextView>(R.id.LoginNow)
        radioGroup = findViewById(R.id.radioGroup)
        radioButtonAdmin = findViewById(R.id.radioButtonAdmin)
        radioButtonNotAdmin = findViewById(R.id.radioButtonNotAdmin)

        clickToSignIn.setOnClickListener {
            intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        signUP.setOnClickListener {
            val email: String
            val password: String
            email = emaill.text.toString()
            password = pass.text.toString()
            progressbar.visibility = View.VISIBLE

            if (email.isEmpty()) {
                val toast = Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT)
                toast.show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                val toast = Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT)
                toast.show()
                return@setOnClickListener
            }

            if (radioGroup.checkedRadioButtonId == -1) {
                // No radio button selected
                Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (radioButtonAdmin.isChecked) {
                // Admin option selected
                Toast.makeText(this, "You can't sign up as Admin.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    progressbar.visibility = View.GONE
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Register Success.", Toast.LENGTH_SHORT).show()
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e(TAG, "createUserWithEmailAndPassword:failure", task.exception)
                        Toast.makeText(baseContext, "Register failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
