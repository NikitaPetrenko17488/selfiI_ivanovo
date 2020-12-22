package com.example.selfii_ivanovo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.selfii_ivanovo.R
import kotlinx.android.synthetic.main.fragment_main_window_admin.*


class mainWindowAdminFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_window_admin, container, false)
    }

    override fun onStart() {
        super.onStart()
        btn4Admin.setOnClickListener {
            findNavController().navigate(R.id.settingsAdminFragment)
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
}