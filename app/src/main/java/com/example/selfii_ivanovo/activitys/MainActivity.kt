package com.example.selfii_ivanovo.activitys

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.mtelevizor.models.User
import com.example.povar.ui.db.MyDbManager
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.fragments.mainWindowFragment
import com.example.selfii_ivanovo.ui.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_alert_add_name.view.*
import java.text.SimpleDateFormat
import java.util.*

var Massiv_User = mutableListOf<User>()

class MainActivity : AppCompatActivity() {

    val myDbManager = MyDbManager(this)
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFireStore()
        loadGamburgerAndToolbar()

    }

    override fun onStart() {
        super.onStart()
        loadDateToday()
        userInfoFun()

    }



    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    fun loadGamburgerAndToolbar()
    {
        AUTH= FirebaseAuth.getInstance()
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navMain) as NavHostFragment? ?: return
        navController = host.navController

        // включаем боковое меню
        val sideBar = findViewById<NavigationView>(R.id.nav_view)
        sideBar?.setupWithNavController(navController)

        // настраиваем и включаем панель инструментов (toolbar)
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout = drawerLayoutMain) // для бокового меню
        val toolBar = findViewById<Toolbar>(R.id.toolbarMain)
        setSupportActionBar(toolBar) // для верхнего меню
        toolBar.setupWithNavController(navController, appBarConfiguration)
    }

    fun delLocal(){
        myDbManager.openDb()
        myDbManager.deleteDb()
    }

    private fun userInfoFun(){
        REF_DABATABSE_ROOT.child(NODE_USERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    //Toast.makeText(this@MainActivity, "Нет подключения к базе..", Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot: DataSnapshot in dataSnapshot.children) {
                        val user = snapshot.getValue(User::class.java) ?: User()
                        if(user.id==STORAGE_USER.id) {
                            STORAGE_USER.number=user.number
                            STORAGE_USER.name=user.name
                            STORAGE_USER.password=user.password
                            STORAGE_USER.dateSelfi=user.date_selfi
                            STORAGE_USER.selfi_path=user.selfi_path
                            Massiv_User.add(user)
                            proverkaNaImya()
                            break
                        }

                    }
                }
            })
    }

    private fun proverkaNaImya() {
        if(Massiv_User.isNotEmpty())
            if(Massiv_User[0].name.isEmpty())
            {
                val builder= AlertDialog.Builder(this)
                val mDialogView = LayoutInflater.from (this) .inflate (R.layout.dialog_alert_add_name, null )
                builder.setView(mDialogView)
                val show=builder.show()
                mDialogView.btnAddName.setOnClickListener {
                    if(mDialogView.editAddName.text.toString().isNotEmpty()) {
                        val mEditName=mDialogView.editAddName.text.toString()
                        Massiv_User[0].name = mEditName
                        REF_DABATABSE_ROOT.child(NODE_USERS).child(STORAGE_USER.id)
                            .child(CHIELD_USERS_NAME).setValue(mEditName).addOnCompleteListener {
                                if(it.isSuccessful)
                                { show.dismiss()}
                            }

                    }
                    else
                    {
                        mDialogView.editAddName.setBackgroundResource(R.drawable.ramka_edit_text_red)
                        showToastA("Введите имя")
                    }
                }
            }
    }

    private fun loadDateToday() {
        val sdf = SimpleDateFormat("yyyy-M-dd")
        STORAGE.currentDate= sdf.format(Date())
    }
}