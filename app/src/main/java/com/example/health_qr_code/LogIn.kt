package com.example.health_qr_code

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_log_in.*

class LogIn : AppCompatActivity() {
    private lateinit var firebase : FirebaseAuth
    var database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        firebase = FirebaseAuth.getInstance()
        if(firebase.currentUser !=null && firebase.currentUser?.isEmailVerified == true  ){
        checkIfUserIsLogged()
        }
        signUpBtn.setOnClickListener {
            startActivity(Intent(this, signUp::class.java))
        }
        signInBtn.setOnClickListener {
            val email = emailET.editText!!.text.toString().trim()
            val pass = passwordET.editText!!.text.toString().trim()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebase.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            if (firebase.currentUser?.isEmailVerified == true) {

                                startActivity(Intent(this, container::class.java))
                            } else {
                                firebase.currentUser?.sendEmailVerification()
                                Toast.makeText(this, "verify your email", Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }

                    }
            } else {
                Toast.makeText(this, "cant be an empty filed", Toast.LENGTH_SHORT).show()
            }
        }
        forgetPassBtn.setOnClickListener {
            val email = emailET.editText!!.text.toString().trim()
            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {
                firebase.sendPasswordResetEmail(email).addOnSuccessListener{
                    Toast.makeText(this, "a reset password email has been sent to $email", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "There is no user registered with that email", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "there is no email or the email is badly formatted", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkIfUserIsLogged() {
        if (firebase.currentUser != null){
            startActivity(Intent(this,container::class.java))
        }
    }

}