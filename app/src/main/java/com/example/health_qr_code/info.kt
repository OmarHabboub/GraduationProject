package com.example.health_qr_code

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_info.call911Btn
import kotlinx.android.synthetic.main.activity_info.progressBar
import kotlinx.android.synthetic.main.activity_info.visible
import java.time.LocalDate
import java.time.Period

class info : AppCompatActivity() {
    private var database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        var QR = intent.extras?.getString("QR")
        var str = QR!!.substring(22)
        database.getReference("Patient").child(str).get().addOnSuccessListener {
            if(it.exists()) {
                fullNameEdit1.text = it.child("fullName").value.toString()
                genderEdit1.text = it.child("gender").value.toString()
                var birthDate = it.child("birthdate").value.toString()
                var  a = birthDate.split("/")
                val dob = LocalDate.of(a[2].toInt(),a[1].toInt(), a[0].toInt()) // date of birth
                val today = LocalDate.now() // current date
                ageEdit.text = Period.between(dob, today).years.toString()
                phoneNumberEdit1.setText(it.child("phoneNumber").value.toString())
                emergencyContactEdit1.setText( it.child("emergencyContact").value.toString())
                addressEdit1.setText(it.child("address").value.toString())
                bloodTypeEdit1.text = it.child("bloodType").value.toString()
                heightEdit1.text = it.child("height").value.toString()
                weightEdit1.text = it.child("weight").value.toString()
                chronicDiseasesEdit1.text = it.child("chronicDiseases").value.toString()
                surgeriesEdit1.text = it.child("surgeries").value.toString()
                familyMedicalHistoryEdit1.text = it.child("familyMedicalHistory").value.toString()
                diseasesEdit1.text = it.child("diseases").value.toString()
                medicinesEdit1.text = it.child("medicines").value.toString()
                foodAllergiesEdit1.text = it.child("foodAllergies").value.toString()
                drugAllergiesEdit1.text = it.child("drugAllergies").value.toString()
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
                        phoneNumberEdit1.setText(snapshot.child("phoneNumber").value.toString())
                        emergencyContactEdit1.setText( snapshot.child("emergencyContact").value.toString())
                        addressEdit1.setText(snapshot.child("address").value.toString())
                        bloodTypeEdit1.text = snapshot.child("bloodType").value.toString()
                        heightEdit1.text = snapshot.child("height").value.toString()
                        weightEdit1.text = snapshot.child("weight").value.toString()
                        chronicDiseasesEdit1.text = snapshot.child("chronicDiseases").value.toString()
                        surgeriesEdit1.text = snapshot.child("surgeries").value.toString()
                        familyMedicalHistoryEdit1.text = snapshot.child("familyMedicalHistory").value.toString()
                        diseasesEdit1.text = snapshot.child("diseases").value.toString()
                        medicinesEdit1.text = snapshot.child("medicines").value.toString()
                        foodAllergiesEdit1.text = snapshot.child("foodAllergies").value.toString()
                        drugAllergiesEdit1.text = snapshot.child("drugAllergies").value.toString()
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
    }
}