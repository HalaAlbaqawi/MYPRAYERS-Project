package com.example.prayerproject.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.prayerproject.R
import com.google.firebase.auth.FirebaseAuth


private lateinit var sharedPref: SharedPreferences
private lateinit var sharedPrefEditor: SharedPreferences.Editor
var SHARED_PREF_FILE = "preference"

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val emailAddress: EditText = findViewById(R.id.login_emailEditText)
        val password: EditText = findViewById(R.id.login_passwordEditText)
        val loginButton: Button = findViewById(R.id.login_button)
        val registerTextView: TextView = findViewById(R.id.login_textView)

        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("is Logged", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        loginButton.setOnClickListener {
            val email: String = emailAddress.text.toString().trim()
            val password: String = password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "you're Logged in Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Navigate to Main Activity
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("UserId", FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("Email", FirebaseAuth.getInstance().currentUser!!.email)
                            sharedPref =
                                this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                            sharedPrefEditor = sharedPref.edit()
                            sharedPrefEditor.putBoolean("is Logged", true)
                            sharedPrefEditor.commit()
                            sharedPref =
                                this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                            sharedPrefEditor = sharedPref.edit()
                            sharedPrefEditor.putBoolean("notifi", true)
                            sharedPrefEditor.commit()
                            startActivity(intent)
                            finish()
                        } else {
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
}