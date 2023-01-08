package com.example.health_qr_code

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up4.*

class SignUp4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up4)
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