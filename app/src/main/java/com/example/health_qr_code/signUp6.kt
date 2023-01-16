package com.example.health_qr_code

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up5.*
import kotlinx.android.synthetic.main.activity_sign_up6.*
import kotlinx.android.synthetic.main.activity_sign_up6.confirmPasswordET
import kotlinx.android.synthetic.main.activity_sign_up6.emailET
import kotlinx.android.synthetic.main.activity_sign_up6.passAllTV
import kotlinx.android.synthetic.main.activity_sign_up6.passLengthTV
import kotlinx.android.synthetic.main.activity_sign_up6.passLowerTV
import kotlinx.android.synthetic.main.activity_sign_up6.passNumberTV
import kotlinx.android.synthetic.main.activity_sign_up6.passSpecialTV
import kotlinx.android.synthetic.main.activity_sign_up6.passUpperTV
import kotlinx.android.synthetic.main.activity_sign_up6.passwordET
import kotlinx.android.synthetic.main.activity_sign_up6.signUpBtn

class signUp6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up6)
        var user = intent.getSerializableExtra("user") as user
        user.verified = false
        val fireBaseAuth = FirebaseAuth.getInstance()
        val database = Firebase.database
        val myRef = database.getReference("MedicalStaff")
        checkPasswordInput(passwordET)
        signUpBtn.setOnClickListener {
            val email = emailET.editText!!.text.toString().trim()
            val pass = passwordET.editText!!.text.toString().trim()
            val passCon = confirmPasswordET.editText!!.text.toString().trim()
            val medicalID = medicalID.text.toString().trim()
            if(email.isNotEmpty()&&pass.isNotEmpty()&&passCon.isNotEmpty()&&medicalID.isNotEmpty()){
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(pass==passCon){
                    if(medicalID.length == 9){
                        fireBaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                            if(it.isSuccessful){
                                myRef.child(fireBaseAuth.currentUser!!.uid).setValue(user)
                                fireBaseAuth.currentUser?.sendEmailVerification()
                                val intent = Intent(applicationContext, LogIn::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.putExtra("EXIT", true)
                                startActivity(intent)
                            }
                            else Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this, "please enter a valid medical ID", Toast.LENGTH_SHORT).show()
                    }
                }
                else Toast.makeText(this, "Passwords in not matching", Toast.LENGTH_SHORT).show()
            }
                else Toast.makeText(this, "please enter a valid email", Toast.LENGTH_SHORT).show()
        }
            else Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()

        }

    }
    private fun checkPasswordStrength(password: String): Boolean {
        val regex = Regex(pattern = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_-])(?=\S+$).{8,16}$""")
        return regex.matches(password)
    }
    private fun containsLowercase(input: String): Boolean {
        return input.contains(Regex(pattern = "[a-z]"))
    }
    private fun containsUppercase(input: String): Boolean {
        return input.contains(Regex(pattern = "[A-Z]"))
    }
    private fun containsSpecialCharacter(input: String): Boolean {
        return input.contains(Regex(pattern = "[@#\$%^&+=_-]"))
    }
    private fun containsNumber(input: String): Boolean {
        return input.contains(Regex(pattern = "[0-9]"))
    }
    private fun isValidLength(input: String): Boolean {
        return input.length in 8..16
    }

    private fun checkPasswordInput(textInputLayout: TextInputLayout) {
        val editText = textInputLayout.editText
        editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // This function is called after the text in the EditText has changed.
                val password = s.toString()
                if (containsLowercase(password)&&containsLowercase(password)&&containsNumber(password)&&containsSpecialCharacter(password)){
                    passAllTV.setTextColor(Color.GREEN)
                }else{
                    passAllTV.setTextColor(Color.RED)
                }
                if(containsLowercase(password)){
                    passLowerTV.setTextColor(Color.GREEN)
                }else{
                    passLowerTV.setTextColor(Color.RED)
                }
                if(containsUppercase(password)){
                    passUpperTV.setTextColor(Color.GREEN)
                }else{
                    passUpperTV.setTextColor(Color.RED)
                }
                if(containsSpecialCharacter(password)){
                    passSpecialTV.setTextColor(Color.GREEN)
                }else{
                    passSpecialTV.setTextColor(Color.RED)
                }
                if(containsNumber(password)){
                    passNumberTV.setTextColor(Color.GREEN)
                }else{
                    passNumberTV.setTextColor(Color.RED)
                }
                if(isValidLength(password)){
                    passLengthTV.setTextColor(Color.GREEN)
                }else{
                    passLengthTV.setTextColor(Color.RED)
                }
                signUpBtn.isEnabled = checkPasswordStrength(password)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                signUpBtn.isEnabled = false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}