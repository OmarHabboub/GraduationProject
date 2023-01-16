package com.example.health_qr_code

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import kotlinx.android.synthetic.main.activity_sign_up1.*
import kotlinx.android.synthetic.main.activity_sign_up3.*
import kotlinx.android.synthetic.main.activity_sign_up3.nextBtn

class SignUp3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up3)
        otherChronicET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    val currentText = s.toString()
                    val lastCharacter = currentText[start + count - 1]
                    if (!lastCharacter.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'1234567890 ]+".toRegex())) {
                        otherChronicET.removeTextChangedListener(this)
                        otherChronicET.text.delete(start, start + count)
                        otherChronicET.addTextChangedListener(this)}
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        surgeriesET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    val currentText = s.toString()
                    val lastCharacter = currentText[start + count - 1]
                    if (!lastCharacter.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'1234567890 ]+".toRegex())) {
                        surgeriesET.removeTextChangedListener(this)
                        surgeriesET.text.delete(start, start + count)
                        surgeriesET.addTextChangedListener(this)}
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        familyMedicalHistoryET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    val currentText = s.toString()
                    val lastCharacter = currentText[start + count - 1]
                    if (!lastCharacter.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'1234567890 ]+".toRegex())) {
                        familyMedicalHistoryET.removeTextChangedListener(this)
                        familyMedicalHistoryET.text.delete(start, start + count)
                        familyMedicalHistoryET.addTextChangedListener(this)}
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        diseasesET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    val currentText = s.toString()
                    val lastCharacter = currentText[start + count - 1]
                    if (!lastCharacter.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'1234567890 ]+".toRegex())) {
                        diseasesET.removeTextChangedListener(this)
                        diseasesET.text.delete(start, start + count)
                        diseasesET.addTextChangedListener(this)}
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        medicinesET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    val currentText = s.toString()
                    val lastCharacter = currentText[start + count - 1]
                    if (!lastCharacter.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'1234567890 ]+".toRegex())) {
                        medicinesET.removeTextChangedListener(this)
                        medicinesET.text.delete(start, start + count)
                        medicinesET.addTextChangedListener(this)}
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        var user = intent.getSerializableExtra("user") as user
        nextBtn.setOnClickListener{
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