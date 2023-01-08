package com.example.health_qr_code

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up3.*

class SignUp3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up3)
        var user = intent.getSerializableExtra("user") as user
        nextBtn.setOnClickListener {
            var surgeries = surgeriesET.text.toString().trim()
            var familyMed = familyMedicalHistoryET.text.toString().trim()
            var diseases = diseasesET.text.toString().trim()
            var medicines = medicinesET.text.toString().trim()
            var chronic = ""
            if(cholesterolCh.isChecked) chronic+=cholesterolCh.text.toString() + ","
            if(diabetesCh.isChecked) chronic+=diabetesCh.text.toString() + ","
            if(hypertensionCh.isChecked) chronic+=hypertensionCh.text.toString() + ","
            if(arthritisCh.isChecked) chronic+=arthritisCh.text.toString() + ","
            if(heartDiseaseCh.isChecked) chronic+=heartDiseaseCh.text.toString() + ","
            if(alzheimerCh.isChecked) chronic+=alzheimerCh.text.toString() + ","
            if(chronicKidneyDiseaseCh.isChecked) chronic+=chronicKidneyDiseaseCh.text.toString() + ","
            if(heartFailureCh.isChecked) chronic+=heartFailureCh.text.toString() + ","
            chronic+=otherChronicET.text.toString().trim()
            if(chronic.endsWith(",")) chronic = chronic.dropLast(1)
            user.surgeries=surgeries
            user.familyMedicalHistory=familyMed
            user.diseases=diseases
            user.medicines=medicines
            user.chronicDiseases=chronic
            var intent = Intent(this,SignUp4::class.java)
            intent.putExtra("user",user)
            startActivity(intent)

        }

    }
}