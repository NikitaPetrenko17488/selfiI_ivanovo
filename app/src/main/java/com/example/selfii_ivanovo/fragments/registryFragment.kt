package com.example.selfii_ivanovo.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.activitys.MainActivity
import com.example.selfii_ivanovo.activitys.RegistryActivity
import com.example.selfii_ivanovo.ui.*
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_registry.*
import kotlinx.android.synthetic.main.fragment_registry.*
import java.util.concurrent.TimeUnit


class registryFragment : Fragment() {

    private lateinit var phoneNumber:String
    private lateinit var mCallback:PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registry, container, false)
    }

    override fun onStart() {
        super.onStart()
       // activity!!.toolbarRegistry.setTitle("Регистрация")
        AUTH = FirebaseAuth.getInstance()
        mCallback=object :PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        startActivity(Intent(activity, MainActivity::class.java))
                    }
                    else
                        showToast(task.exception?.message.toString())
                }
            }

            override fun onVerificationFailed(credential: FirebaseException) {
               showToast(credential.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                STORAGE_REGISTRY.code=phoneNumber
                STORAGE_REGISTRY.id=id
                findNavController().navigate(R.id.enterCodeFragment)
            }
        }
        btnRegistryStep1.setOnClickListener {
            sendMessage()
        }
        btnAvtorizyInRegistry.setOnClickListener { replaceRegistry(avtorizyFragment()) }
    }

    override fun onPause() {
        super.onPause()
        FirebaseAuth.getInstance().signOut()
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

    fun sendMessage()
    {
        if(editNumberRegistry.text.toString().isEmpty())
        {
            showToast(" Введите номер ")
        }
        else
        {
            authUser()
        }
    }

    @SuppressLint("ResourceType")
    private fun authUser() {
        phoneNumber=editNumberRegistry.text.toString()
        STORAGE_REGISTRY.number=phoneNumber
        if(phoneNumber.length<12){
            editNumberRegistry.setBackgroundResource(R.drawable.ramka_edit_text_red)
            showToast("Введите номер состоящий из 11 цифр")
        }
        else {
            PhoneAuthProvider.verifyPhoneNumber(
                PhoneAuthOptions
                    .newBuilder(FirebaseAuth.getInstance())
                    .setActivity(activity as RegistryActivity)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setCallbacks(mCallback)
                    .build()
            )
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,
//                10,
//                TimeUnit.SECONDS,
//                activity as RegistryActivity,
//                mCallback
//
//            )
        }
    }
}