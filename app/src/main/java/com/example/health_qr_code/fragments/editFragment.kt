package com.example.health_qr_code.fragments

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.health_qr_code.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up1.*
import kotlinx.android.synthetic.main.activity_sign_up3.*
import kotlinx.android.synthetic.main.activity_sign_up4.*
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.diseasesET
import kotlinx.android.synthetic.main.fragment_edit.familyMedicalHistoryET
import kotlinx.android.synthetic.main.fragment_edit.medicinesET
import kotlinx.android.synthetic.main.fragment_edit.surgeriesET
import java.time.LocalDate
import java.time.Period

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [editFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class editFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var timer: CountDownTimer
    lateinit var ref : String
    var database = Firebase.database
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addressET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'#-_ 1234567890]+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            addressET.removeTextChangedListener(this)
                            addressET.setText(str)
                            addressET.setSelection(start)
                            addressET.addTextChangedListener(this)}
                    }

                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        chronicDiseaseET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'#-_ 1234567890 ]+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            chronicDiseaseET.removeTextChangedListener(this)
                            chronicDiseaseET.setText(str)
                            chronicDiseaseET.setSelection(start)
                            chronicDiseaseET.addTextChangedListener(this)}
                    }

                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        surgeriesET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'#-_ 1234567890 ]+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            surgeriesET.removeTextChangedListener(this)
                            surgeriesET.setText(str)
                            surgeriesET.setSelection(start)
                            surgeriesET.addTextChangedListener(this)}
                    }

                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        familyMedicalHistoryET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'#-_ 1234567890 ]+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            familyMedicalHistoryET.removeTextChangedListener(this)
                            familyMedicalHistoryET.setText(str)
                            familyMedicalHistoryET.setSelection(start)
                            familyMedicalHistoryET.addTextChangedListener(this)}
                    }

                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        diseasesET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'#-_ 1234567890 ]+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            diseasesET.removeTextChangedListener(this)
                            diseasesET.setText(str)
                            diseasesET.setSelection(start)
                            diseasesET.addTextChangedListener(this)}
                    }
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        medicinesET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'#-_ 1234567890 ]+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            medicinesET.removeTextChangedListener(this)
                            medicinesET.setText(str)
                            medicinesET.setSelection(start)
                            medicinesET.addTextChangedListener(this)}
                    }

                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        foodAllergiesET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'#-_ 1234567890 ]+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            foodAllergiesET.removeTextChangedListener(this)
                            foodAllergiesET.setText(str)
                            foodAllergiesET.setSelection(start)
                            foodAllergiesET.addTextChangedListener(this)}
                    }

                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        drugAllergiesET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var str = s.toString()
                if(str.isNotEmpty()){
                    for (c in s.toString()){
                        if (!c.toString().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,'#-_ 1234567890 ]+".toRegex())) {
                            str = str.replace(c.toString(),"")
                            drugAllergiesET.removeTextChangedListener(this)
                            drugAllergiesET.setText(str)
                            drugAllergiesET.setSelection(start)
                            drugAllergiesET.addTextChangedListener(this)}
                    }

                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        val fireBaseAuth = FirebaseAuth.getInstance()
        timer = object : CountDownTimer(200, 200) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                database.getReference("Patient").child(fireBaseAuth.currentUser!!.uid).get().addOnSuccessListener {
                    if(it.exists()) {
                        ref = "Patient"
                        fullNameET.text = it.child("fullName").value.toString()
                        genderET.text = it.child("gender").value.toString()
                        var birthDate = it.child("birthdate").value.toString()
                        var a = birthDate.split("/")
                        val dob =
                            LocalDate.of(a[2].toInt(), a[1].toInt(), a[0].toInt()) // date of birth
                        val today = LocalDate.now() // current date
                        ageET.text = Period.between(dob, today).years.toString()
                        var phoneNum = it.child("phoneNumber").value.toString().subSequence(startIndex = 4, endIndex = 13)
                        phoneNumberET.setText(phoneNum)
                        var emergencyContact = it.child("emergencyContact").value.toString().subSequence(startIndex = 4 , endIndex = 13)
                        emergencyContactET.setText( emergencyContact)
                        addressET.setText(it.child("address").value.toString())
                        bloodET.text = it.child("bloodType").value.toString()
                        heightET.setText(it.child("height").value.toString())
                        weightET.setText(it.child("weight").value.toString())
                        chronicDiseaseET.setText(it.child("chronicDiseases").value.toString())
                        surgeriesET.setText(it.child("surgeries").value.toString())
                        familyMedicalHistoryET.setText(it.child("familyMedicalHistory").value.toString())
                        diseasesET.setText(it.child("diseases").value.toString())
                        medicinesET.setText(it.child("medicines").value.toString())
                        foodAllergiesET.setText(it.child("foodAllergies").value.toString())
                        drugAllergiesET.setText(it.child("drugAllergies").value.toString())
                        progressBar.isInvisible = true
                        visible.isInvisible = true
                    }
                    else {
                        database.getReference("MedicalStaff").child(fireBaseAuth.currentUser!!.uid).get().addOnSuccessListener { snapshot ->
                            if(snapshot.exists()){
                                ref = "MedicalStaff"
                                fullNameET.text = snapshot.child("fullName").value.toString()
                                genderET.text = snapshot.child("gender").value.toString()
                                var birthDate = snapshot.child("birthdate").value.toString()
                                var  a = birthDate.split("/")
                                val dob = LocalDate.of(a[2].toInt(),a[1].toInt(), a[0].toInt()) // date of birth
                                val today = LocalDate.now() // current date
                                ageET.text = Period.between(dob, today).years.toString()
                                var phoneNum = snapshot.child("phoneNumber").value.toString().subSequence(startIndex = 4, endIndex = 13)
                                phoneNumberET.setText(phoneNum)
                                var emergencyContact = snapshot.child("emergencyContact").value.toString().subSequence(startIndex = 4 , endIndex = 13)
                                emergencyContactET.setText( emergencyContact)
                                addressET.setText(snapshot.child("address").value.toString())
                                bloodET.text = snapshot.child("bloodType").value.toString()
                                heightET.setText(snapshot.child("height").value.toString())
                                weightET.setText(snapshot.child("weight").value.toString())
                                chronicDiseaseET.setText(snapshot.child("chronicDiseases").value.toString())
                                surgeriesET.setText(snapshot.child("surgeries").value.toString())
                                familyMedicalHistoryET.setText(snapshot.child("familyMedicalHistory").value.toString())
                                diseasesET.setText(snapshot.child("diseases").value.toString())
                                medicinesET.setText(snapshot.child("medicines").value.toString())
                                foodAllergiesET.setText(snapshot.child("foodAllergies").value.toString())
                                drugAllergiesET.setText(snapshot.child("drugAllergies").value.toString())
                                progressBar.isInvisible = true
                                visible.isInvisible = true
                            }
                            else {
                             Toast.makeText(context, "Try again later", Toast.LENGTH_SHORT).show()}
                }.addOnFailureListener {
                            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                    }}}.addOnFailureListener{
                        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()}
            }
        }.start()

        saveBtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmation")
            builder.setMessage("Are you sure you want to save the new information?")
            builder.setPositiveButton("Yes") { _, _ ->
                if (isValidJordanianPhoneNumber(phoneNumberET.text.toString()) || isValidJordanianPhoneNumber(emergencyContactET.text.toString())){
                    if(addressET.text.isNotEmpty()){
                        if (weightET.text.isNotEmpty()&& isValidWeight(weightET.text.toString())){
                            if (heightET.text.isNotEmpty()&& isValidHeight(heightET.text.toString())){
                                var user = mapOf(
                                    "phoneNumber" to "+962"+ phoneNumberET.text.toString(),
                                    "emergencyContact" to "+962"+emergencyContactET.text.toString(),
                                    "address" to addressET.text.toString(),
                                    "height" to heightET.text.toString(),
                                    "weight" to weightET.text.toString(),
                                    "chronicDiseases" to chronicDiseaseET.text.toString(),
                                    "surgeries" to surgeriesET.text.toString(),
                                    "familyMedicalHistory" to familyMedicalHistoryET.text.toString(),
                                    "diseases" to diseasesET.text.toString(),
                                    "medicines" to medicinesET.text.toString(),
                                    "foodAllergies" to foodAllergiesET.text.toString(),
                                    "drugAllergies" to drugAllergiesET.text.toString())
                                database.getReference(ref).child(fireBaseAuth.currentUser!!.uid).updateChildren(user).addOnSuccessListener {
                                    Toast.makeText(this.context, "Updates has been saved", Toast.LENGTH_SHORT).show()
                                }
                                    .addOnFailureListener{
                                        Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                                    }
                            }else Toast.makeText(context, "please enter a valid height", Toast.LENGTH_SHORT).show()
                        }else Toast.makeText(context, "please enter a valid weight", Toast.LENGTH_SHORT).show()
                    }else Toast.makeText(context, "address can't be empty", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(context, "please enter a valid number", Toast.LENGTH_SHORT).show()
                }            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val dialog: AlertDialog = builder.create()
            dialog.setCancelable(false)
            dialog.show()
        }

        discardBtn.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.container,editFragment())
            fragmentTransaction?.commit()
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

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment editFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            editFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}