package com.example.health_qr_code.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.CountDownTimer
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
import kotlinx.android.synthetic.main.fragment_edit.*
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
        val fireBaseAuth = FirebaseAuth.getInstance()
        timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                database.getReference("Patient").child(fireBaseAuth.currentUser!!.uid).get().addOnSuccessListener {
                    ref = "Patient"
                    if(it.exists()){
                        fullNameET.text = it.child("fullName").value.toString()
                        genderET.text = it.child("gender").value.toString()
                        var birthDate = it.child("birthdate").value.toString()
                        var  a = birthDate.split("/")
                        val dob = LocalDate.of(a[2].toInt(),a[1].toInt(), a[0].toInt()) // date of birth
                        val today = LocalDate.now() // current date
                        ageET.text = Period.between(dob, today).years.toString()
                        phoneNumberET.setText(it.child("phoneNumber").value.toString())
                        emergencyContactET.setText( it.child("emergencyContact").value.toString())
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
                        Toast.makeText(context, "Try again later", Toast.LENGTH_SHORT).show()}

                }.addOnFailureListener{
                    database.getReference("MedicalStaff").child(fireBaseAuth.currentUser!!.uid).get().addOnSuccessListener {
                        ref = "MedicalStaff"
                        if(it.exists()){
                            fullNameET.text = it.child("fullName").value.toString()
                            genderET.text = it.child("gender").value.toString()
                            var birthDate = it.child("birthdate").value.toString()
                            var  a = birthDate.split("/")
                            val dob = LocalDate.of(a[2].toInt(),a[1].toInt(), a[0].toInt()) // date of birth
                            val today = LocalDate.now() // current date
                            ageET.text = Period.between(dob, today).years.toString()
                            phoneNumberET.setText(it.child("phoneNumber").value.toString())
                            emergencyContactET.setText( it.child("emergencyContact").value.toString())
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
                            Toast.makeText(context, "Try again later", Toast.LENGTH_SHORT).show()}
                    }.addOnFailureListener{
                        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()}
                }
            }
        }.start()


        saveBtn.setOnClickListener {
            if (isValidJordanianPhoneNumber(phoneNumberET.text.toString()) || isValidJordanianPhoneNumber(emergencyContactET.text.toString())){
                if(addressET.text.isNotEmpty()){
                    if (weightET.text.isNotEmpty()){
                        if (heightET.text.isNotEmpty()){
                            var user = mapOf(
                                "phoneNumber" to phoneNumberET.text.toString(),
                                "emergencyContact" to emergencyContactET.text.toString(),
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
                        }else Toast.makeText(context, "height can't be empty", Toast.LENGTH_SHORT).show()
                    }else Toast.makeText(context, "weight can't be empty", Toast.LENGTH_SHORT).show()
                }else Toast.makeText(context, "address can't be empty", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context, "please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }
        discardBtn.setOnClickListener {

        }


    }
    private fun isValidJordanianPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches("^\\+962(77|78|79)\\d{7}$".toRegex())
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