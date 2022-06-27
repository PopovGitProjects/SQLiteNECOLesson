package com.example.sqlitenecolesson.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.sqlitenecolesson.models.Model

class DBManager(context: Context) {
    private val dbHelper = DBHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDB(){
        db = dbHelper.writableDatabase
    }

    fun insertToDB(title: String, content: String){
        val values = ContentValues().apply {
            put(DBConstants.COLUMN_NAME_TITLE, title)
            put(DBConstants.COLUMN_NAME_CONTENT, content)
        }
        db?.insert(DBConstants.TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun readDBData(): ArrayList<Model>{
        val dataList = ArrayList<Model>()
        val projection = arrayOf(BaseColumns._ID, DBConstants.COLUMN_NAME_TITLE, DBConstants.COLUMN_NAME_CONTENT)
        val cursor = db?.query(DBConstants.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor){
            while (this?.moveToNext()!!){
                val title = cursor?.getString(cursor.getColumnIndex(DBConstants.COLUMN_NAME_TITLE))
                val content = cursor?.getString(cursor.getColumnIndex(DBConstants.COLUMN_NAME_CONTENT))
                dataList.add(Model(title.toString(), content.toString()))
            }
        }
        cursor?.close()
        return dataList
    }
    fun closeDb(){
        dbHelper.close()
    }
}