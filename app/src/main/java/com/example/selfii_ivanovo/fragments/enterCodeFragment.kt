package com.example.selfii_ivanovo.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mtelevizor.models.User
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.ui.AUTH
import com.example.selfii_ivanovo.ui.STORAGE_REGISTRY
import com.example.selfii_ivanovo.ui.showToast
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*


class enterCodeFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_code, container, false)
    }

    override fun onStart() {
        super.onStart()

        btnRegistryStep2.setOnClickListener {
            if(editEnterCodeFragment.text.toString().isEmpty())
            {
                showToast(" Введите код ")
            }
            else
            {
                enterCode()
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun enterCode() {
        val code=editEnterCodeFragment.text.toString()
        val credential=PhoneAuthProvider.getCredential(STORAGE_REGISTRY.id,code)
        AUTH.signInWithCredential(credential).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                findNavController().navigate(R.id.addPasswordFragment)
            }
            else {
                editEnterCodeFragment.setBackgroundResource(R.drawable.ramka_edit_text_red)
                showToast("Неверный код")
            }
        }
    }
}