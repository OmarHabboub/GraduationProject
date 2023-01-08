package com.example.health_qr_code

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        check()
        setContentView(R.layout.activity_main)
        nextBtn.setOnClickListener {
            startActivity(Intent(this, LogIn::class.java))
        }

    }

    private fun check() {
        val pref = getSharedPreferences("ActivityPREF", MODE_PRIVATE)
        if (pref.getBoolean("activity_executed", false)) {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()
        } else {
            val ed: SharedPreferences.Editor = pref.edit()
            ed.putBoolean("activity_executed", true)
            ed.commit()
        }
    }
}