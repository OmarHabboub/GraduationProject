package com.example.health_qr_code

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import kotlinx.android.synthetic.main.activity_sign_up3.*
import kotlinx.android.synthetic.main.activity_sign_up4.*
import kotlinx.android.synthetic.main.activity_sign_up4.nextBtn

class SignUp4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up4)
        otherFoodAllergiesET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    val currentText = s.toString()
                    val lastCharacter = currentText[start + count - 1]
                    if (!lastCharacter.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'1234567890 ]+".toRegex())) {
                        otherFoodAllergiesET.removeTextChangedListener(this)
                        otherFoodAllergiesET.text.delete(start, start + count)
                        otherFoodAllergiesET.addTextChangedListener(this)}
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        otherDrugAllergiesET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    val currentText = s.toString()
                    val lastCharacter = currentText[start + count - 1]
                    if (!lastCharacter.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'1234567890 ]+".toRegex())) {
                        otherDrugAllergiesET.removeTextChangedListener(this)
                        otherDrugAllergiesET.text.delete(start, start + count)
                        otherDrugAllergiesET.addTextChangedListener(this)}
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        var user = intent.getSerializableExtra("user") as user
        nextBtn.setOnClickListener {
            var foodAller = ""
            var drugAller = ""
            if(buckwheatCh.isChecked) foodAller+=buckwheatCh.text.toString() + ","
            if(milkCh.isChecked) foodAller+=milkCh.text.toString() + ","
            if(celeryCh.isChecked) foodAller+=celeryCh.text.toString() + ","
            if(peanutCh.isChecked) foodAller+=peanutCh.text.toString() + ","
            if(fishCh.isChecked) foodAller+=fishCh.text.toString() + ","
            if(fruitCh.isChecked) foodAller+=fruitCh.text.toString() + ","
            if(riceCh.isChecked) foodAller+=riceCh.text.toString() + ","
            if(wheatCh.isChecked) foodAller+=wheatCh.text.toString() + ","
            foodAller+=otherFoodAllergiesET.text.toString().trim()
            if(foodAller.endsWith(","))  foodAller=foodAller.dropLast(1)

            if (tetracyclineCh.isChecked) drugAller+= tetracyclineCh.text.toString() + ","
            if (penicillinCh.isChecked) drugAller+= penicillinCh.text.toString() + ","
            if (dilantinCh.isChecked) drugAller+= dilantinCh.text.toString() + ","
            if (cephalosporinsCh.isChecked) drugAller+= cephalosporinsCh.text.toString() + ","
            if (tegretolCh.isChecked) drugAller+= tegretolCh.text.toString() + ","
            if (sulfonamidesCh.isChecked) drugAller+= sulfonamidesCh.text.toString() + ","
            if (localAnestheticsCh.isChecked) drugAller+= localAnestheticsCh.text.toString() + ","
            if (balsamOfPeruCh.isChecked) drugAller+= balsamOfPeruCh.text.toString() + ","
            drugAller+=otherDrugAllergiesET.text.toString().trim()
            if(drugAller.endsWith(","))  drugAller=drugAller.dropLast(1)

            user.foodAllergies=foodAller
            user.drugAllergies=drugAller
            if(user.auth == "patient"){
                var intent = Intent(this,signUp5::class.java)
                intent.putExtra("user",user)
                startActivity(intent)}
            else{
                var intent = Intent(this,signUp6::class.java)
                intent.putExtra("user",user)
                startActivity(intent)
            }
        }
    }
}