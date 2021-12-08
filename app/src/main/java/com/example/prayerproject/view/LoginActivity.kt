package com.example.athu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.prayerproject.MainActivity
import com.example.prayerproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val emailAddress:EditText = findViewById(R.id.emailAddress_EditText)
        val password:EditText = findViewById(R.id.password_EditText)
        val loginButton:Button = findViewById(R.id.login_button)
        val registerTextView: TextView = findViewById(R.id.register_TextView)

        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        loginButton.setOnClickListener {
            val email: String = emailAddress.text.toString()
            val password: String = password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                            task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "User Logged in Successfully", Toast.LENGTH_SHORT).show()
                            // Navigate to Main Activity
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("UserId", FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("Email",FirebaseAuth.getInstance().currentUser!!.email)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}