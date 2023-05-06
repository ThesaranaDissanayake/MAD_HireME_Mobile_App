package com.example.hireme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.hireme.R

class CategoryActivity : AppCompatActivity() {

    private lateinit var btnHome : Button
    private lateinit var btnTech : Button
    private lateinit var btnFree : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        btnHome = findViewById(R.id.btnHome)
        btnTech = findViewById(R.id.btnTech)
        btnFree = findViewById(R.id.btnFree)

        btnHome.setOnClickListener{
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        btnTech.setOnClickListener{
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        btnFree.setOnClickListener{
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }
    }
}