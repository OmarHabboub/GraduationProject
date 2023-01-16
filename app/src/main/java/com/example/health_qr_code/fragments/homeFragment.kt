
package com.example.health_qr_code.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import com.example.health_qr_code.R
import com.example.health_qr_code.LogIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [homeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class homeFragment : Fragment() {
    private lateinit var timer: CountDownTimer
    var database = Firebase.database
    private lateinit var bitmap: Bitmap
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fireBaseAuth = FirebaseAuth.getInstance()
        timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                database.getReference("Patient").child(fireBaseAuth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        if (it.exists()) {
                            authView.text = "Patient"
                            progressBar.isInvisible = true
                            visible.isInvisible = true
                        } else {
                            database.getReference("MedicalStaff")
                                .child(fireBaseAuth.currentUser!!.uid).get().addOnSuccessListener {
                                if (it.exists()) {
                                    authView.text = "Medical Staff"
                                    progressBar.isInvisible = true
                                    visible.isInvisible = true
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Try again later medical",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }.addOnFailureListener {
                                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show() }
                        }
                    }.addOnFailureListener {
                                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                            }
                            val writer = QRCodeWriter()
                            val bitMatrix = writer.encode(
                                "www.HealthQr_Code.com/" + fireBaseAuth.currentUser!!.uid,
                                BarcodeFormat.QR_CODE, 512, 512
                            )
                            val width = bitMatrix.width
                            val height = bitMatrix.height
                            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                            for (x in 0 until width) {
                                for (y in 0 until height) {
                                    bitmap.setPixel(
                                        x,
                                        y,
                                        if (bitMatrix[x, y]) Color.BLUE else Color.WHITE
                                    )
                                }
                            }
                            QR_View.setImageBitmap(bitmap)
                        }
            }.start()



        printBtn.setOnClickListener {

        }


        call911Btn.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:911")
            startActivity(intent)
        }
        logOutBtn.setOnClickListener {
            fireBaseAuth.signOut()
            val intent = Intent(context, LogIn::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Stop the countdown timer when the fragment is destroyed
        timer.cancel()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment homeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            homeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}