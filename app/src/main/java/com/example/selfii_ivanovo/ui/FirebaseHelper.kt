package com.example.selfii_ivanovo.ui
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.snapshot.Node
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var AUTH: FirebaseAuth
lateinit var REF_DABATABSE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT:StorageReference

const val NODE_COMMON="common"
const val NODE_USERS = "users"
const val FOLDER_USERS_SELFI="users_selfi"
const val NODE_TODAY_LIKES="today_likes"

const val CHIELD_COMMON_COUNTER_USERS="counter_users"


const val CHIELD_USERS_ID="id"
const val CHIELD_USERS_NAME="name"
const val CHIELD_USERS_NUMBER="number"
const val CHIELD_USERS_PASSWORD="password"
const val CHIELD_USERS_SELFI_PATH="selfi_path"
const val CHIELD_USER_DATE_SELFI="date_selfi"
const val CHIELD_USERS_RAITING="raiting"


const val CHIELD_TODAY_USER_ID="user_id"
const val CHIELD_TODAY_USER_ID_SELF="user_id_self"
const val CHIELD_TODAY_DATE="date"
const val CHIELD_TODAY_OCENKA="ocenka"


fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DABATABSE_ROOT = FirebaseDatabase.getInstance().reference
    REF_STORAGE_ROOT=FirebaseStorage.getInstance().reference
}

