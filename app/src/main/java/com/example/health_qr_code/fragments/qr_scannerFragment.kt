package com.example.health_qr_code.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.health_qr_code.R
import com.example.health_qr_code.info
import com.example.health_qr_code.info2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_qr_scanner.*
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [qr_scannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class qr_scannerFragment : Fragment(){
    var database = Firebase.database
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr_scanner, container, false)
    }
    private val CAMERA_REQUEST_CODE = 101
    private lateinit var codeScanner: CodeScanner
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fireBaseAuth = FirebaseAuth.getInstance().currentUser!!.uid
        var au = ""
        database.getReference("Patient").child(fireBaseAuth).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    au = it.child("auth").value.toString()
                } else {
                    database.getReference("MedicalStaff").child(fireBaseAuth)
                        .get().addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                au = snapshot.child("auth").value.toString()
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

        setUpPermission()
        val activity = requireActivity()
        codeScanner = CodeScanner(activity,scanner)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false
        }
        codeScanner.startPreview()
        codeScanner.decodeCallback = DecodeCallback {
            lifecycleScope.launch {
                if(it.text.length >= 22){
                    if (it.text.substring(0,22).compareTo("www.HealthQr_Code.com/") == 0){
                        if (au == "medicalStaff"){
                        var intent = Intent(context, info::class.java)
                        intent.putExtra("QR",it.text)
                        startActivity(intent)
                        }else{
                            var intent = Intent(context, info2::class.java)
                            intent.putExtra("QR",it.text)
                            startActivity(intent)
                        }
                         }
                    else{
                        fail.text = "The Qr code that you are reading is not known to me , try again"
                    }
                }
                else{
                    fail.text = "The Qr code that you are reading is not known to me , try again"
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            Toast.makeText(context, "Camera initialization error: ${it.message}",
                Toast.LENGTH_LONG).show()
        }

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment qr_scannerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            qr_scannerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun setUpPermission(){
        val permission = ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.CAMERA)
        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(requireActivity(),"you need to accept camera permission",Toast.LENGTH_SHORT)
                }
                else{

                }
            }
        }
    }
}