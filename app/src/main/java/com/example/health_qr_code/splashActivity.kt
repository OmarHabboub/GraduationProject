package com.example.health_qr_code

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val data: Uri? = intent?.data
        if (data != null) {
            var database = Firebase.database
            val fireBaseAuth = FirebaseAuth.getInstance().currentUser
            if (fireBaseAuth != null){
            database.getReference("Patient").child(fireBaseAuth.uid).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                                var intent = Intent(this, info2::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                                intent.putExtra("EXIT", true)
                                intent.putExtra("QR", data.toString())
                                startActivity(intent)
                    } else {
                        database.getReference("MedicalStaff").child(fireBaseAuth.uid)
                            .get().addOnSuccessListener { snapshot ->
                                if (snapshot.exists()) {
                                    var intent = Intent(this, info::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                                    intent.putExtra("QR", data.toString())
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Try again later medical",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }.addOnFailureListener {
                                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                            }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
            }else{
                var intent = Intent(this, info2::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                intent.putExtra("QR", data.toString())
                startActivity(intent)
            }
        }else{
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 1000)}
    }
}
