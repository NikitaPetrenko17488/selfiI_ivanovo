package com.example.selfii_ivanovo.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.povar.ui.db.MyDbManager
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.activitys.MainActivity
import com.example.selfii_ivanovo.activitys.Massiv_User
import com.example.selfii_ivanovo.activitys.RegistryActivity
import com.example.selfii_ivanovo.ui.STORAGE_USER
import kotlinx.android.synthetic.main.fragment_settings.*


class settingsFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onStart() {
        super.onStart()
        exit.setOnClickListener {
            exitFun()
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

    private fun exitFun() {
        Massiv_User.removeAll { true }
       val act:MainActivity=activity as MainActivity
        act.delLocal()
        STORAGE_USER.number=""
        STORAGE_USER.name=""
        STORAGE_USER.password=""
        STORAGE_USER.dateSelfi=""
        STORAGE_USER.selfi_path=""
        startActivity(Intent(activity,RegistryActivity::class.java))

    }
}
