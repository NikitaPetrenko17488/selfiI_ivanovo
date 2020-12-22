package com.example.povar.ui.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.fragment.app.FragmentActivity
import com.example.mtelevizor.models.User
import com.example.mtelevizor.models.User_LocalBase

class MyDbManager(context: FragmentActivity?) {
    val myDbHelper=MyDbHelper(context!!)
    var db:SQLiteDatabase?=null

    fun openDb(){
        db=myDbHelper.writableDatabase
    }
    fun insertToDb( number:String,auth:String,admin:Int)
    {
        val Values=ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_NUMBER, number)
            put(MyDbNameClass.COLUMN_NAME_AUTH, auth)
            put(MyDbNameClass.COLUMN_NAME_ADMIN, admin)
        }
        db?.insert(MyDbNameClass.TABLE_NAME,null, Values)
    }
    fun ReadDb():ArrayList<User_LocalBase>{
        val dataList= arrayListOf<User_LocalBase>()
        val cursor =db?.query(MyDbNameClass.TABLE_NAME,null,null,null,
            null,null,null)


           while (cursor?.moveToNext()!!){
               var dataNumber=cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_NUMBER))
               var dataAuth=cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_AUTH))
               var dataAdmin=cursor.getInt(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_ADMIN))


               dataList.add(User_LocalBase(dataNumber,dataAuth,dataAdmin))
               dataNumber=""
               dataAuth=""
               dataAdmin=0
            }

        cursor.close()
        return dataList

    }
    fun closeDb(){
        myDbHelper.close()
    }

    fun deleteDb(){

        val deletedRows = db?.delete(MyDbNameClass.TABLE_NAME, null,null)
    }

}
