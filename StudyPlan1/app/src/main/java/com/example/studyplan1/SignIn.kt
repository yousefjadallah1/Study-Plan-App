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
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignIn : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    lateinit var auth:FirebaseAuth
    lateinit var progressbarr: ProgressBar
  public override fun onStart() {
       super.onStart()
       // check if user is signed in
       val currentUser = auth.currentUser
        if (currentUser != null) {
            if (currentUser.email == "yousef@hotmail.com"){
                intent = Intent(this, AdminUse::class.java)
                startActivity(intent)
                finish()
            }else {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
       }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        val emaill: TextInputEditText = findViewById(R.id.email)
        val passwordd: TextInputEditText = findViewById(R.id.password)
        val signIn: Button = findViewById(R.id.signin)
        val signInAdmin: Button = findViewById(R.id.signinAdmin)
        progressbarr = findViewById(R.id.progressBar)
        val signupclick = findViewById<TextView>(R.id.SignupNow)

        signupclick.setOnClickListener {
            intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }

        signIn.setOnClickListener {
            val email: String = emaill.text.toString()
            val password: String = passwordd.text.toString()
            progressbarr.visibility = View.VISIBLE

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

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progressbarr.visibility = View.GONE
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        if (email == "yousef@hotmail.com" && password == "123456"){
                            intent = Intent(this, MainActivity::class.java)
                            Toast.makeText(baseContext, "Login Success", Toast.LENGTH_SHORT,).show()
                            startActivity(intent)
                            finish()
                        } else {
                            intent = Intent(this, MainActivity::class.java)
                            Toast.makeText(baseContext, "Login Success", Toast.LENGTH_SHORT,).show()
                            startActivity(intent)
                            finish()
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Login failed.", Toast.LENGTH_SHORT,).show()
                    }
                }


        }
        signInAdmin.setOnClickListener {
            val email: String = emaill.text.toString()
            val password: String = passwordd.text.toString()
            progressbarr.visibility = View.VISIBLE

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

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progressbarr.visibility = View.GONE
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        if (email == "yousef@hotmail.com" && password == "123456"){
                            intent = Intent(this, AdminUse::class.java)
                            Toast.makeText(baseContext, "Login Success", Toast.LENGTH_SHORT,).show()
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(baseContext, "Login failed, you are not Admin", Toast.LENGTH_SHORT,).show()
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_SHORT,).show()
                    }
                }


        }

    }
}