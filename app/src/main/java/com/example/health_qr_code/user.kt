package com.example.health_qr_code

import java.io.Serializable

data class user(var auth: String? ="", var fullName:String="", var Gender:String="", var birthdate:String="",
                var phoneNumber: String="", var emergencyContact:String="",
                var address:String="",var bloodType:String="", var height:String="", var weight:String="",
                var chronicDiseases:String="", var surgeries:String="", var familyMedicalHistory:String="",
                var diseases:String="", var medicines:String="", var foodAllergies:String="",
                var drugAllergies:String="",var verified : Boolean = true): Serializable
