package com.example.health_qr_code

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*

class signUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        patientBtn.setOnClickListener {
            var auth = "patient"
            var intent = Intent(this,signUp1::class.java)
            intent.putExtra("auth",auth)
            startActivity(intent)
        }
        medicalStaffBtn.setOnClickListener {
            var auth = "medicalStaff"
            var intent = Intent(this,signUp1::class.java)
            intent.putExtra("auth",auth)
            startActivity(intent)
        }
    }
}