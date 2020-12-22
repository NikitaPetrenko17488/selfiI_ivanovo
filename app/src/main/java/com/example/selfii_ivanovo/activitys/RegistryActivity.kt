package com.example.selfii_ivanovo.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.povar.ui.db.MyDbManager
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.ui.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_add_password.*

class RegistryActivity : AppCompatActivity() {

    val myDbManager = MyDbManager(this)

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)
        initFireStore()
        initFirebase()
        AUTH = FirebaseAuth.getInstance()
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navRegistry) as NavHostFragment? ?: return
         navController = host.navController

    }

    override fun onStart() {
        super.onStart()
        autoProverka()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().signOut()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    private fun autoProverka()
    {
        myDbManager.openDb()
        val dataList=myDbManager.ReadDb()
        myDbManager.closeDb()
        if(dataList.isNotEmpty()) {
            STORAGE_USER.id = dataList[0].number

        }
          //  Toast.makeText(applicationContext, dataList.toString(),Toast.LENGTH_SHORT).show()
        if(dataList.isNotEmpty()) {
            if (dataList[0].auth == 1.toString()) {
                if(dataList[0].admin==1)
                {startActivity(Intent(this, AdminActivity::class.java))}
                else
                {startActivity(Intent(this, MainActivity::class.java))}

            }
        }
    }

     fun addInLocalBase(admin:Int){
         myDbManager.openDb()
        myDbManager.insertToDb(
            STORAGE_REGISTRY.number,
            1.toString(),
            admin)

    }
}