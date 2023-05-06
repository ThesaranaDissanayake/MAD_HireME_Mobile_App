package com.example.hireme.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.hireme.R
import com.example.hireme.adapters.EmpAdapter
import com.example.hireme.databinding.ActivityHomeBinding
import com.example.hireme.databinding.ActivityProfileBinding
import com.example.hireme.models.FeedbackModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

class ProfileActivity : AppCompatActivity() {

    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var btnRateNow : Button
    private lateinit var btnSeeAll : Button

    private lateinit var rvList : ArrayList<FeedbackModel>
    private lateinit var firebase : DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnRateNow = findViewById(R.id.button_1)
        btnSeeAll = findViewById(R.id.button_see)

        btnRateNow.setOnClickListener {
            val intent = Intent(this, AddReview::class.java)
            startActivity(intent)
        }
        btnSeeAll.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        firebase = FirebaseDatabase.getInstance().getReference("Reviews")

        rvList = arrayListOf()

        //fetchData()

    }

}