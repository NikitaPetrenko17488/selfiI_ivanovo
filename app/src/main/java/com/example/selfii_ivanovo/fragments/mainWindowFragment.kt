package com.example.selfii_ivanovo.fragments

import android.os.Bundle
import android.text.BoringLayout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mtelevizor.models.User
import com.example.mtelevizor.models.today_likes
import com.example.selfii_ivanovo.R
import com.example.selfii_ivanovo.activitys.AdminActivity
import com.example.selfii_ivanovo.activitys.MainActivity
import com.example.selfii_ivanovo.ui.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_main_window.*
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class mainWindowFragment : Fragment() {

    var krugP=0
    val db = FirebaseFirestore.getInstance()
    var counterload=0
    var Massiv_UsersMainWindow = mutableListOf<User>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_window, container, false)
    }

    override fun onStart() {
        super.onStart()
        init()
        unLoadPhoto()
        loadDataBase()
        btnAddSelfi.setOnClickListener {
            replaceMain(addSelfiFragment())
        }

        like.setOnClickListener {
            likeFun()
        }

        dislike.setOnClickListener {
            dislikeFun()
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

    private fun init() {
        krugP=0
        counterload=0
        Massiv_UsersMainWindow.removeAll { true }
    }

    private fun unLoadPhoto() // если всех просвайпал то хуй а не загрузка фотки
    {

        if(counterload>=Massiv_UsersMainWindow.size) {
            noPhotoMainWindow.visibility = View.VISIBLE
            like.visibility = View.INVISIBLE
            dislike.visibility = View.INVISIBLE
            photoMainWindow.visibility = View.INVISIBLE
            toastNoUsers.setText("Вы просмотрели все селфи")
            photoMainWindow.setImageResource(R.drawable.no_photo)
            nameNaPhoto.setText(" ")
        }
        else {
            toastNoUsers.setText("Загрузка")
        }
    }

    private fun loadPhoto() { // загрузка фотки

        if(counterload<Massiv_UsersMainWindow.size){
            Massiv_UsersMainWindow.shuffle()
            like.visibility=View.VISIBLE
            dislike.visibility=View.VISIBLE
            photoMainWindow.visibility=View.VISIBLE
            noPhotoMainWindow.visibility=View.INVISIBLE
                 photoMainWindow.downloadSetImage(Massiv_UsersMainWindow[counterload].selfi_path,R.drawable.no_photo)
                nameNaPhoto.setText(Massiv_UsersMainWindow[counterload].name)
                toastNoUsers.setText("")
            }
            else
            {
                unLoadPhoto()
            }
    }

    private fun  loadDataBase() {  // загрузка с файбейс акка и по нему с файрстора всех кого не свайпал
        krugP=0
        REF_DABATABSE_ROOT.child(NODE_USERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity, "Нет подключения к базе..", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var count = 0
                    Massiv_UsersMainWindow.removeAll { true }
                    for (snapshot: DataSnapshot in dataSnapshot.children) {

                        val user = snapshot.getValue(User::class.java) ?: User()
                        if (user.number != STORAGE_USER.id && user.selfi_path.isNotEmpty() && user.date_selfi == STORAGE.currentDate) {
                            Massiv_UsersMainWindow.add(user)

                            db.collection(NODE_TODAY_LIKES)
                                .whereEqualTo(CHIELD_TODAY_USER_ID,
                                    STORAGE_USER.id + " " + user.number
                                )
                                .get()
                                .addOnSuccessListener { task ->
                                    for (document in task) {
                                        if (document.data[CHIELD_TODAY_USER_ID].toString()
                                                .isNotEmpty()
                                        ) {
                                            Massiv_UsersMainWindow.removeAt(count)
                                            count--
                                        }
                                        count++

                                    }
                                    krug()
                                }
                        }
                    }

                }
            })
    }

    fun krug() // что бы не было хуйни при загрузке аля секундноя закгрузка фотки, бред и переделать бы
    {
        var count=0
        if(krugP!=2) {
            krugP = 2
            GlobalScope.launch {
                while (count < 1) {
                    delay(200)
                    if (krugP != 1) {
                        GlobalScope.launch(Dispatchers.Main) {
                            loadPhoto()
                        }
                        break
                    }
                }
            }
        }
    }


    private fun likeFun() {  // при свайпе лайк
        var ocenka=Massiv_UsersMainWindow[counterload].raiting
        if(counterload<Massiv_UsersMainWindow.size) {
            ocenka++
            REF_DABATABSE_ROOT.child(NODE_USERS).child(Massiv_UsersMainWindow[counterload].id)
                .child(CHIELD_USERS_RAITING).setValue(ocenka).addOnCompleteListener { task3 ->
                    if (task3.isSuccessful) {
                        counterload++
                        today_likesFun(ocenka)
                        loadPhoto()
                    }
                }

        }


    }


    private fun dislikeFun() {// при свайпе дизлайк
        var ocenka=0
        if(counterload<Massiv_UsersMainWindow.size){ // если текущий счетчик меньше ,чем всех кого не свайпал
            today_likesFun(ocenka)
        counterload++
        loadPhoto()
        }
    }

    private fun today_likesFun( ocenka:Int) {  // типа если свайп то в файрСтор
        val dateMap =
            mutableMapOf<String, Any>()

        dateMap[CHIELD_TODAY_USER_ID] = STORAGE_USER.id + " " + Massiv_UsersMainWindow[counterload].id
        dateMap[CHIELD_TODAY_DATE] =STORAGE.currentDate
        dateMap[CHIELD_TODAY_OCENKA]=ocenka

            db.collection(NODE_TODAY_LIKES)
                .add(dateMap)


    }
}