package com.example.health_qr_code
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up2.*

class signUp2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)
        var user = intent.getSerializableExtra("user") as user
        var blood_type = ""
        var types = resources.getStringArray(R.array.Blood_types)
        var adapter1: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.blood_spinner, types)
        spinner.adapter = adapter1
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                blood_type = types[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        nextBtn.setOnClickListener {
            var phoneNumber = phoneNumberET.text.toString().trim()
            var emergencyContact = EmergencyContactET.text.toString().trim()
            var weight = weightET.text.toString().trim()
            var height = heightET.text.toString().trim()
            if(phoneNumber.isEmpty()||emergencyContact.isEmpty()||blood_type==""||weight.isEmpty()||height.isEmpty()){
                Toast.makeText(this, "every field must be filled", Toast.LENGTH_LONG).show()
            }
            else{
                if(isValidJordanianPhoneNumber(phoneNumber)){
                    if (isValidJordanianPhoneNumber(emergencyContact)){
                        if(isValidWeight(weight)){
                            if (isValidHeight(height)){
                                user.phoneNumber= "+962$phoneNumber"
                                user.emergencyContact="+962$emergencyContact"
                                user.bloodType=blood_type
                                user.weight=weight
                                user.height=height
                                var intent = Intent(this,SignUp3::class.java)
                                intent.putExtra("user",user)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "please enter a valid height", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this, "please enter a valid weight", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this, "please enter a valid emergency contact", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "please enter a valid phone number", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }
    private fun isValidJordanianPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches("^(77|78|79)\\d{7}$".toRegex())
    }
    private fun isValidWeight(weight: String): Boolean {
        return weight.toInt() in 21..399
    }
    private fun isValidHeight(height: String): Boolean {
        return height.toInt() in 80..250
    }
}