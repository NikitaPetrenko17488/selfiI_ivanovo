package com.example.selfii_ivanovo.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.activitys.MainActivity
import com.example.selfii_ivanovo.ui.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.text.SimpleDateFormat
import java.util.*


class addSelfiFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_selfi, container, false)
    }

    override fun onStart() {
        super.onStart()
        startSelfiFun()
    }

    private fun startSelfiFun() {
        CropImage.activity()
            .setAspectRatio(1,1)
            .setRequestedSize(1200,1200)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(activity as MainActivity,this)
        replaceMain(mainWindowFragment())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val sdf = SimpleDateFormat("yyyy-M-dd")
        val currentDate = sdf.format(Date())

        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode== Activity.RESULT_OK &&  data!=null)
        {
            val uri = CropImage.getActivityResult(data).uri
            val path= REF_STORAGE_ROOT.child(FOLDER_USERS_SELFI)
                .child(STORAGE_USER.id)
            path.putFile(uri).addOnCompleteListener {
                if(it.isSuccessful)
                {
                    path.downloadUrl.addOnCompleteListener{
                        if(it.isSuccessful){
                            val photoUrl=it.result.toString()
                            STORAGE_USER.selfi_path=photoUrl
                            STORAGE_USER.dateSelfi=currentDate
                            REF_DABATABSE_ROOT.child(NODE_USERS).child(STORAGE_USER.id)
                                .child(CHIELD_USERS_SELFI_PATH).setValue(photoUrl).addOnCompleteListener { task2->
                                    if(task2.isSuccessful)
                                    {
                                        REF_DABATABSE_ROOT.child(NODE_USERS).child(STORAGE_USER.id)
                                            .child(CHIELD_USER_DATE_SELFI).setValue(currentDate).addOnCompleteListener { task3 ->
                                                if (task3.isSuccessful) {


                                                }
                                            }

                                    }
                                }

                        }

                    }
                }

            }
        }

    }
}