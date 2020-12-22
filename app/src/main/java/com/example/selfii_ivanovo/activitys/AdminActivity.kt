package com.example.selfii_ivanovo.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.povar.ui.db.MyDbManager
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.ui.STORAGE_REGISTRY
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class AdminActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navAdmin) as NavHostFragment? ?: return
        navController = host.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        val toolbar = findViewById<Toolbar>(R.id.toolbarAdmin)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()
    }

    fun delLocal(){
        myDbManager.openDb()
        myDbManager.deleteDb()
    }
}