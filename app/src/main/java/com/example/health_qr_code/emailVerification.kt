package com.example.health_qr_code
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_email_verification.*

class emailVerification : AppCompatActivity() {
    val firebaseAuth = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)
        var user = intent.getSerializableExtra("user") as user
        val database = Firebase.database
        val myRef = database.getReference("Patient")
        object : CountDownTimer(600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val firebaseAuth = FirebaseAuth.getInstance().currentUser
                firebaseAuth?.reload()?.addOnSuccessListener {
                    if (firebaseAuth.isEmailVerified) {
                        myRef.child(firebaseAuth.uid).setValue(user)
                        val intent = Intent(applicationContext, LogIn::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.putExtra("EXIT", true)
                        startActivity(intent)
                        cancel()
                    } else {
                        val minutes = millisUntilFinished / 60000
                        val seconds = (millisUntilFinished / 1000) % 60
                        timerTextView.text = "Time remaining-> $minutes:$seconds seconds"
                    }
                }
            }

            override fun onFinish() {
                if(firebaseAuth?.isEmailVerified == false){
                    firebaseAuth?.delete()
                }
            }
        }.start()

        resendBtn.setOnClickListener {
            firebaseAuth?.sendEmailVerification()
            resendBtn.isEnabled = false
            object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    resendBtn.text = "${R.string.resend.toString()} : ${millisUntilFinished / 1000} seconds"
                }
                override fun onFinish() {
                    resendBtn.isEnabled = true
                    resendBtn.text = R.string.resend.toString()
                }
            }.start()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if(firebaseAuth?.isEmailVerified == false){
            firebaseAuth?.delete()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(firebaseAuth?.isEmailVerified == false){
            firebaseAuth?.delete()
        }
    }

}
