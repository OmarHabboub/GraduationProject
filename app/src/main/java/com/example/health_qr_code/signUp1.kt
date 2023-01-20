package com.example.health_qr_code
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextThemeWrapper
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up1.*
import java.util.*

class signUp1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up1)

        FirstNameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[a-zA-Z,']+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            FirstNameET.removeTextChangedListener(this)
                            FirstNameET.setText(str)
                            FirstNameET.setSelection(start)
                            FirstNameET.addTextChangedListener(this)}
                    }
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })

        MiddleNameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()) {
                        if (!c.toString().matches("[a-zA-Z,']+".toRegex())
                        ) {
                            str = str.replace(c.toString(), "")
                            MiddleNameET.removeTextChangedListener(this)
                            MiddleNameET.setText(str)
                            MiddleNameET.setSelection(start)
                            MiddleNameET.addTextChangedListener(this)
                        }
                    }
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        LastNameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[a-zA-Z,']+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            LastNameET.removeTextChangedListener(this)
                            LastNameET.setText(str)
                            LastNameET.setSelection(start)
                            LastNameET.addTextChangedListener(this)}
                }
            }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        AddressET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[a-zA-Z,'#-_ 1234567890 ]+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            AddressET.removeTextChangedListener(this)
                            AddressET.setText(str)
                            AddressET.setSelection(start)
                            AddressET.addTextChangedListener(this)}
                    }

                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        var auth = intent.extras?.getString("auth")
        var user: user
        var birthDate = ""
        BirthDateBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val month = c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)
            val maxDate = Calendar.getInstance()
            maxDate.add(Calendar.YEAR, -10)
            val minDate = Calendar.getInstance()
            minDate.add(Calendar.YEAR, -100)
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay ->
                birthDate = "$mDay/${mMonth+1}/$mYear"
                BirthDateBtn.text = "$mDay/${mMonth+1}/$mYear"

            },year,month,day)
            val datePicker = dpd.datePicker
            datePicker.maxDate = maxDate.timeInMillis
            datePicker.minDate = minDate.timeInMillis
            dpd.show()
            dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE)
            dpd.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
        }
        var gender = ""
        toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.Male_btn -> gender = "Male"
                    R.id.Female_btn -> gender = "Female"
                }
            }}
        nextBtn.setOnClickListener {
            val firstName = FirstNameET.text.toString().trim()
            val middleName = MiddleNameET.text.toString().trim()
            val lastName = LastNameET.text.toString().trim()
            val fullName = "$firstName $middleName $lastName"
            val address = AddressET.text.toString().trim()
            if(firstName.isEmpty()||middleName.isEmpty()||lastName.isEmpty()||gender.isEmpty()||birthDate.isEmpty()||address.isEmpty()){
                Toast.makeText(this, "every field must be filled", Toast.LENGTH_SHORT).show()
            }
            else{
                user = user(auth = auth, fullName = fullName, Gender = gender, birthdate = birthDate,
                    address = address)
                var intent = Intent(this,signUp2::class.java)
                intent.putExtra("user",user)
                startActivity(intent)
            }}

    }
}