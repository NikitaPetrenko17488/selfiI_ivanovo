package com.example.selfii_ivanovo.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mtelevizor.models.User
import com.example.mtelevizor.models.common_data
import com.example.povar.ui.db.MyDbManager
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.activitys.MainActivity
import com.example.selfii_ivanovo.activitys.RegistryActivity
import com.example.selfii_ivanovo.ui.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_add_password.*


class addPasswordFragment : Fragment() {

    var Massiv_Common = mutableListOf<common_data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_password, container, false)
    }

    override fun onStart() {
        super.onStart()
        loadDataBase()
        btnRegistryStep3.setOnClickListener{
            if(editPasswordAddPassword.text.toString().isEmpty())
            {
                showToast("Введите пароль")
            }
            else {
                if(editPasswordAddPassword.text.toString().length>=6) {
                    addPassword()
                }
                else {
                    editPasswordAddPassword.setBackgroundResource(R.drawable.ramka_edit_text_red)
                    showToast("Пароль должен содержать минимум 6 символов")
                }
            }
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

    private fun addPassword() {
        val dateMap =
            mutableMapOf<String, Any>()

        val act:RegistryActivity= activity as RegistryActivity
        act.addInLocalBase(0)
        var ID=STORAGE_REGISTRY.number
        dateMap[CHIELD_USERS_ID] = ID
        dateMap[CHIELD_USERS_NUMBER] =STORAGE_REGISTRY.number
        dateMap[CHIELD_USERS_PASSWORD]=editPasswordAddPassword.text.toString()
        STORAGE_USER.name=editPasswordAddPassword.text.toString()

        STORAGE_USER.id=ID

        REF_DABATABSE_ROOT.child(NODE_USERS).child(ID)
            .updateChildren(dateMap).addOnCompleteListener {
                if(it.isSuccessful)
                {
                    common_counterUsersFun()
                }
            }
    }

    private fun common_counterUsersFun() {
        val dateMap =
            mutableMapOf<String, Any>()
        var counter_users=Massiv_Common[0].counter_users
        counter_users++
        dateMap[CHIELD_COMMON_COUNTER_USERS]=counter_users
        REF_DABATABSE_ROOT.child(NODE_COMMON).child(CHIELD_COMMON_COUNTER_USERS)
            .updateChildren(dateMap).addOnCompleteListener {
                if(it.isSuccessful)
                {
                    startActivity(Intent(activity,MainActivity::class.java))
                }
            }

    }


    private fun loadDataBase() {
        REF_DABATABSE_ROOT.child(NODE_COMMON).child(CHIELD_COMMON_COUNTER_USERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity, "Нет подключения к базе..", Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val common = dataSnapshot.getValue(common_data::class.java) ?: common_data()
                        Massiv_Common.add(common)

                    }
            })
    }
}