package com.example.health_qr_code

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_info.ageEdit
import kotlinx.android.synthetic.main.activity_info.bloodTypeEdit1
import kotlinx.android.synthetic.main.activity_info.call911Btn
import kotlinx.android.synthetic.main.activity_info.callEmergencyBtn
import kotlinx.android.synthetic.main.activity_info.emergencyContactEdit1
import kotlinx.android.synthetic.main.activity_info.fullNameEdit1
import kotlinx.android.synthetic.main.activity_info.genderEdit1
import kotlinx.android.synthetic.main.activity_info.homeButton
import kotlinx.android.synthetic.main.activity_info.progressBar
import kotlinx.android.synthetic.main.activity_info.visible
import java.time.LocalDate
import java.time.Period

class info2 : AppCompatActivity() {
    private var database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info2)

        var QR = intent.extras?.getString("QR")
        var str = QR!!.substring(40)

        database.getReference("Patient").child(str).get().addOnSuccessListener {
            if(it.exists()) {
                fullNameEdit1.text = it.child("fullName").value.toString()
                genderEdit1.text = it.child("gender").value.toString()
                var birthDate = it.child("birthdate").value.toString()
                var  a = birthDate.split("/")
                val dob = LocalDate.of(a[2].toInt(),a[1].toInt(), a[0].toInt()) // date of birth
                val today = LocalDate.now() // current date
                ageEdit.text = Period.between(dob, today).years.toString()
                emergencyContactEdit1.setText( it.child("emergencyContact").value.toString())
                bloodTypeEdit1.text = it.child("bloodType").value.toString()
                visible.isInvisible = true
                progressBar.isInvisible = true
            }
            else {
                database.getReference("MedicalStaff").child(str).get().addOnSuccessListener { snapshot ->
                    if(snapshot.exists()){
                        fullNameEdit1.text = snapshot.child("fullName").value.toString()
                        genderEdit1.text = snapshot.child("gender").value.toString()
                        var birthDate = snapshot.child("birthdate").value.toString()
                        var  a = birthDate.split("/")
                        val dob = LocalDate.of(a[2].toInt(),a[1].toInt(), a[0].toInt()) // date of birth
                        val today = LocalDate.now() // current date
                        ageEdit.text = Period.between(dob, today).years.toString()
                        emergencyContactEdit1.setText( snapshot.child("emergencyContact").value.toString())
                        bloodTypeEdit1.text = snapshot.child("bloodType").value.toString()
                        visible.isInvisible = true
                        progressBar.isInvisible = true
                    }
                    else {
                        Toast.makeText(this, "Try again later", Toast.LENGTH_SHORT).show()}
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
                }}}.addOnFailureListener{
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()}

        call911Btn.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:911")
            startActivity(intent)
        }
        callEmergencyBtn.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${emergencyContactEdit1.text}")
            startActivity(intent)
        }
        homeButton.setOnClickListener{
            val intent = Intent(applicationContext, LogIn::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
    }
}