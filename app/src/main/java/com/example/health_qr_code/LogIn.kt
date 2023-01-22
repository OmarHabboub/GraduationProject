package com.example.health_qr_code

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.popup_layout.view.*

class LogIn : AppCompatActivity() {
    private var firebaseAuth = FirebaseAuth.getInstance()
    private var database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        if(firebaseAuth.currentUser !=null && firebaseAuth.currentUser?.isEmailVerified == true  ){
        checkIfUserIsLogged()
        }
        signUpBtn.setOnClickListener {
            startActivity(Intent(this, signUp::class.java))
        }
        LogInBtn.setOnClickListener {
            val email = emailET.editText!!.text.toString().trim()
            val pass = passwordET.editText!!.text.toString().trim()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            if (firebaseAuth.currentUser?.isEmailVerified == true) {
                                database.getReference("MedicalStaff").child(firebaseAuth.currentUser!!.uid).get().addOnSuccessListener {
                                    if (it.exists()){
                                        if (it.child("verified").value as Boolean){
                                            startActivity(Intent(this, container::class.java))
                                        }else{
                                            Toast.makeText(this, "You need to wait for admin approval", Toast.LENGTH_SHORT).show()
                                        }
                                    }else{
                                        startActivity(Intent(this, container::class.java))
                                    }
                                }.addOnFailureListener{
                                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                firebaseAuth.currentUser?.sendEmailVerification()
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
                firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener{
                    Toast.makeText(this, "a reset password email has been sent to $email", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "There is no user registered with that email", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "there is no email or the email is badly formatted", Toast.LENGTH_SHORT).show()
            }
        }
        helpButton.setOnClickListener {
            val popup = PopupWindow(this)
            popup.isFocusable =  true
            val view = layoutInflater.inflate(R.layout.popup_layout, null)
            val email = view.email_text
            email.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "plain/text"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("omarhaboob00@gmail.com"))
                }
                startActivity(emailIntent)
            }
            popup.contentView = view
            popup.showAsDropDown(helpButton)
        }

    }

    private fun checkIfUserIsLogged() {
        if (firebaseAuth.currentUser != null){
            startActivity(Intent(this,container::class.java))
        }
    }
}