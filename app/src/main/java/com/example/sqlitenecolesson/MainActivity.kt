package com.example.sqlitenecolesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlitenecolesson.databinding.ActivityMainBinding
import com.example.sqlitenecolesson.db.DBManager
import com.example.sqlitenecolesson.models.Model

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dbManager = DBManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    override fun onResume() = with(binding) {
        super.onResume()
        dbManager.openDB()
        val dataList = dbManager.readDBData()
        for (item in dataList){
            tvResult.append(writeModel(item))
            tvResult.append("\n")
        }
    }
    private fun init() = with(binding){
        button.setOnClickListener {
            tvResult.text = ""
            dbManager.insertToDB(edTitle.text.toString(), edContent.text.toString())

            val dataList = dbManager.readDBData()
            for (item in dataList){
                tvResult.append(writeModel(item))
                tvResult.append("\n")
            }
        }
    }
    private fun writeModel(item: Model): String{
        return item.id + "  " + item.title + "\n" + item.content
    }
    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }
}