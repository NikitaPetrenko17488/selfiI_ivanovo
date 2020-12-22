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
import com.example.mtelevizor.models.today_likes
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.activitys.AdminActivity
import com.example.selfii_ivanovo.activitys.MainActivity
import com.example.selfii_ivanovo.activitys.RegistryActivity
import com.example.selfii_ivanovo.ui.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_settings_admin.*


class settingsAdminFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    var Massiv_UsersAdmin = mutableListOf<today_likes>()
    var Massiv_Common = mutableListOf<common_data>()

    var winnerNumber=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_admin, container, false)
    }

    override fun onStart() {
        super.onStart()
        init()
        btnExitAdmin.setOnClickListener {
            exitFun()
        }
        chooseWinner.setOnClickListener {
            winnerFun()
        }

    }

    private fun init() {

            REF_DABATABSE_ROOT.child(NODE_COMMON)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(activity, "Нет подключения к базе..", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
                            val common = snapshot.getValue(common_data::class.java) ?: common_data()
                            Massiv_Common.add(common)
                            textCounterUsers.setText("Кол-во юзеров: "+Massiv_Common[0].counter_users.toString())
                        }
                    }
                })

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




    private fun winnerFun() {
        var count=0
        var countNumber=0
        Massiv_UsersAdmin.removeAll { true }
        db.collection(NODE_TODAY_LIKES)
            .get()
            .addOnSuccessListener { task ->
                for (document in task) {
                    Massiv_UsersAdmin.add(
                        today_likes(
                            document.data[CHIELD_TODAY_USER_ID].toString(),
                            document.data[CHIELD_TODAY_DATE].toString(),
                            document.data[CHIELD_TODAY_OCENKA].toString().toInt()
                        )
                    )
                    dopWinnerFun()

                }
            }

    }

    private fun dopWinnerFun() {

    }

    private fun exitFun() {
        val act: AdminActivity =activity as AdminActivity
        act.delLocal()
        startActivity(Intent(activity, RegistryActivity::class.java))
    }
}