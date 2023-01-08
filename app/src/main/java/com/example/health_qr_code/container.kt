package com.example.health_qr_code

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.health_qr_code.fragments.qr_scannerFragment
import com.example.health_qr_code.fragments.editFragment
import com.example.health_qr_code.fragments.homeFragment
import kotlinx.android.synthetic.main.activity_container.*

private val edit = editFragment()
private val home = homeFragment()
private val qrScannerFragment = qr_scannerFragment()

class container : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
        replaceFragment(home)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.editFragment -> replaceFragment(edit)
                R.id.homeFragment -> replaceFragment(home)
                R.id.scanFragment -> replaceFragment(qrScannerFragment)

            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container,fragment)
            transaction.commit()
        }
    }

    override fun onBackPressed() {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
        }

    override fun onResume() {
        super.onResume()
        bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
        replaceFragment(home)
    }
}


