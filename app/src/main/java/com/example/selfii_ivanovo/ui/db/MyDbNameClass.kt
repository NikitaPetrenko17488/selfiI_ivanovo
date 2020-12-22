package com.example.povar.ui.db

import android.provider.BaseColumns

object MyDbNameClass:BaseColumns {
    const val TABLE_NAME ="user"
    const val COLUMN_NAME_NAME="name"
    const val COLUMN_NAME_NUMBER="number"
    const val COLUMN_NAME_PASSWORD="password"
    const val COLUMN_NAME_AUTH="auth"
    const val COLUMN_NAME_ADMIN="admin"

    const val DATABASE_VERSION=1
    const val DATABASE_NAME="Selfi.db"

    const val CREAT_TABLE ="CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY,$COLUMN_NAME_NAME TEXT,$COLUMN_NAME_NUMBER TEXT,$COLUMN_NAME_PASSWORD TEXT,$COLUMN_NAME_AUTH TEXT,$COLUMN_NAME_ADMIN INT)"

    const val SQL_DELETE_TABLE="DROP TABLE IF EXISTS $TABLE_NAME"
}