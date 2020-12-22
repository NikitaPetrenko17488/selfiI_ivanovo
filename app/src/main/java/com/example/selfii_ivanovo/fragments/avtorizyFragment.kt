package com.example.selfii_ivanovo.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mtelevizor.models.User
import com.example.povar.ui.db.MyDbManager
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.activitys.AdminActivity
import com.example.selfii_ivanovo.activitys.MainActivity
import com.example.selfii_ivanovo.activitys.RegistryActivity
import com.example.selfii_ivanovo.ui.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_registry.*
import kotlinx.android.synthetic.main.fragment_avtorizy.*


class avtorizyFragment : Fragment() {

    var Massiv_Users = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avtorizy, container, false)
    }

    override fun onStart() {
        super.onStart()

        loadDataBase()
        //activity!!.toolbarRegistry.setTitle("Авторизация")
        btnRegistryInAvtorizy.setOnClickListener {
            replaceRegistry(registryFragment())
        }
        btnAvtorizy.setOnClickListener {
            avtorizyFun()
        }
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

    private fun loadDataBase() {
        REF_DABATABSE_ROOT.child(NODE_USERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity, "Нет подключения к базе..", Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot: DataSnapshot in dataSnapshot.children) {
                        val user = snapshot.getValue(User::class.java) ?: User()
                        Massiv_Users.add(user)

                    }
                }
            })
    }

    private fun avtorizyFun() {
        val act: RegistryActivity = activity as RegistryActivity
        var number =editNumberAvtorizy.text.toString()
        var password = editPasswordAvotizy.text.toString()
        var good=0
        if (number.isEmpty() || password.isEmpty())
        {
            showToast(" Введите номер телефона или пароль")
        }
        else
        {

            var count=0
            while (count < Massiv_Users.size)
            {

                 if(Massiv_Users[count].number==number && Massiv_Users[count].password==password)
                {

                    if(Massiv_Users[count].admin==1)
                    {

                        STORAGE_USER.id=number
                        STORAGE_REGISTRY.number=number
                        alertDialogAdminOrUser()
                        good=1

                        break
                    }
                    else {
                        STORAGE_USER.id = number
                        STORAGE_REGISTRY.number = number
                        act.addInLocalBase(0)
                        good = 1
                        startActivity(Intent(activity, MainActivity::class.java))
                    }
                }
                count++
            }
            if(good==0)
            showToast(" Не правильный логин или пароль ")

        }
    }

    private fun alertDialogAdminOrUser()
    {
        val act: RegistryActivity = activity as RegistryActivity
        val builder= AlertDialog.Builder(activity)
        builder.setTitle("Под кем войти?")

        builder.setNeutralButton("Odmen"){Odmen, i ->
            act.addInLocalBase(1)
            startActivity(Intent(activity,AdminActivity::class.java))
        }
        builder.setNegativeButton("User"){User, i ->
            act.addInLocalBase(0)
            startActivity(Intent(activity,MainActivity::class.java))
        }
        builder.show()
    }
}