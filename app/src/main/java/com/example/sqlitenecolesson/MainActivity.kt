package com.example.sqlitenecolesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlitenecolesson.databinding.ActivityMainBinding
import com.example.sqlitenecolesson.db.DBManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dbManager = DBManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }
    private fun init() = with(binding){
        button.setOnClickListener {
            tvResult.text = ""
            dbManager.openDB()
            dbManager.insertToDB(edTitle.text.toString(), edContent.text.toString())

            val dataList = dbManager.readDBData()
            for (item in dataList){
                tvResult.append(item.toString())
                tvResult.append("\n")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }
}