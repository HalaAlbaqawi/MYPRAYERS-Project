package com.example.prayerproject.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.prayerproject.main.MainActivity
import com.example.prayerproject.R
import com.example.prayerproject.util.RegisterValidation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

const val TAG = "RegisterActivity"
private val validator = RegisterValidation()

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val emailAddress: EditText = findViewById(R.id.register_email_address_edittext)
        val password: EditText = findViewById(R.id.register_passowrd_edittext)
        val registerButton: Button = findViewById(R.id.register_button)
        val loginTextView: TextView = findViewById(R.id.login_textView)

        loginTextView.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        registerButton.setOnClickListener() {
            val email: String = emailAddress.text.toString().trim()
            val password: String = password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {

                // To check if your password and email are strong and correct
                if (validator.passwordIsValid(password)) {
                } else
                    Toast.makeText(this, "Make sure your password is strong.", Toast.LENGTH_SHORT)
                        .show()
            } else
                Toast.makeText(
                    this,
                    "Make sure you typed your email address correctly.",
                    Toast.LENGTH_SHORT
                ).show()
            Log.d("RegisterActivity", email)
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseuser: FirebaseUser = task.result!!.user!!
                        Toast.makeText(this, "You Registered Successfully", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("UserId", firebaseuser.uid)
                        intent.putExtra("Email", firebaseuser.email)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("RegisterActivity", task.exception!!.message.toString())

                        Toast.makeText(
                            this,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
