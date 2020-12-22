package com.example.selfii_ivanovo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.activitys.Massiv_User
import com.example.selfii_ivanovo.ui.STORAGE_USER
import com.example.selfii_ivanovo.ui.downloadSetImage
import com.example.selfii_ivanovo.ui.showToast
import kotlinx.android.synthetic.main.fragment_profile_user.*


class profileUserFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_user, container, false)
    }

    override fun onStart() {
        super.onStart()
        //showToast(Massiv_User[0].toString())
        initProfile()

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

    private fun initProfile()
    {
        lastImageProfile.downloadSetImage(STORAGE_USER.selfi_path,R.drawable.no_photo)
        textNameProfile.setText("Имя: " + STORAGE_USER.name)
        textNumberProfile.setText("Номер: " + STORAGE_USER.number)
        textDatePhotoProfile.setText(STORAGE_USER.dateSelfi)

    }
}