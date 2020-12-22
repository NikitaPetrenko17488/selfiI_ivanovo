package com.example.selfii_ivanovo.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.selfii_ivanovo.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*


fun Activity.showToastA(message:String)
{
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()

}

fun Fragment.showToast(message:String)
{
Toast.makeText(this.context,message,Toast.LENGTH_SHORT).show()

}


@SuppressLint("ResourceType")

fun Fragment.replaceMain(fragment: Fragment)
{
    fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(
            R.id.navMain,
            fragment
        )?.commit()

    }

fun Fragment.replaceRegistry(fragment: Fragment)
{
    fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(
            R.id.navRegistry,
            fragment
        )?.commit()

}




fun CircleImageView.downloadSetImage(url:String,imageNoInternet:Int)
{
    if(url.isNotEmpty())
        Picasso.get()
            .load(url)
            .placeholder(imageNoInternet)   ////// запись в картинку
            .into(this)
}

fun ImageView.downloadSetImage(url: String,imageNoInternet:Int)
{
    if(url.isNotEmpty())
        Picasso.get()
            .load(url)
            .placeholder(imageNoInternet)   ////// запись в картинку
            .into(this)

}









