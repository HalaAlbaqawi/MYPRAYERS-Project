package com.example.prayerproject.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.prayerproject.R
import com.example.prayerproject.databinding.ActivityRegisterBinding
import com.example.prayerproject.util.RegisterValidation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


const val TAG = "RegisterActivity"
private val validator = RegisterValidation()

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailFocusListener()
        passwordFocusListener()

        binding.registerButton.setOnClickListener { submitForm() }




        binding.loginTextView.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.registerButton.setOnClickListener() {
            val email: String = binding.registerEmailEditText.text.toString().trim()
            val password: String = binding.registerPasswordEditText.text.toString()
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
                        val intent = Intent(this, LoginActivity::class.java)
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

    // Email and Password Validation
    private fun submitForm() {
        binding.emailContainer.helperText = validEmail()
        binding.passwordContainer.helperText = validPassword()

        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null

        if (validEmail && validPassword)
            resetForm()
        else
            invalidForm()
    }

    private fun invalidForm() {
        var message = ""
        if (binding.emailContainer.helperText != null)
            message += "\n\nEmail: " + binding.emailContainer.helperText
        if (binding.passwordContainer.helperText != null)
            message += "\n\nPassword: " + binding.passwordContainer.helperText

        AlertDialog.Builder(this)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                // do nothing
            }
            .show()
    }

    private fun resetForm() {
        var message = "Email: " + binding.registerEmailEditText.text
        message += "\nPassword: " + binding.registerPasswordEditText.text
        AlertDialog.Builder(this)
            .setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                binding.registerEmailEditText.text = null
                binding.registerPasswordEditText.text = null

                binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
            }
            .show()
    }

    private fun emailFocusListener() {
        binding.registerEmailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.registerEmailEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.registerPasswordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.registerPasswordEditText.text.toString()
        if (passwordText.length < 8) {
            return "Minimum 8 Character Password"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        }
        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

        return null
    }

}
