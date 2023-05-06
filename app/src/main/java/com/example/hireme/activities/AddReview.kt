package com.example.hireme.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hireme.R
import com.example.hireme.models.FeedbackModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddReview : AppCompatActivity() {

    private lateinit var etRvContent : EditText
    private lateinit var btnSubmit : Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_review)

        etRvContent = findViewById(R.id.edtText_1)
        btnSubmit = findViewById(R.id.button_2)

        dbRef = FirebaseDatabase.getInstance().getReference("Reviews")

        btnSubmit.setOnClickListener{
            saveCustomerReviews()
        }

    }

    private fun saveCustomerReviews(){

        val tvContent = etRvContent.text.toString()

        if (tvContent.isEmpty()){
            etRvContent.error = "Please enter name"
        }

        val rvId = dbRef.push().key!!

        val feedback = FeedbackModel(rvId, tvContent)

        dbRef.child(rvId).setValue(feedback).addOnCompleteListener{
            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

        }.addOnFailureListener{err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()

        }


    }
}