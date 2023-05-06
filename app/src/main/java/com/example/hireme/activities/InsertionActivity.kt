package com.example.hireme.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hireme.models.PostModel
import com.example.hireme.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etLocation: EditText
    private lateinit var etAbout: EditText
    private lateinit var etContact: EditText

    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etTitle = findViewById(R.id.etTitle)//ndv
        etLocation = findViewById(R.id.etLocation)
        etAbout = findViewById(R.id.etAbout)
        etContact = findViewById(R.id.etContact)

        btnSaveData = findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("JobPostings")

        btnSaveData.setOnClickListener{
            savePostData()
        }
    }

    private fun savePostData(){
        //getting values
        val pTitle = etTitle.text.toString()
        val pLocation = etLocation.text.toString()
        val pAbout = etAbout.text.toString()
        val pContact = etContact.text.toString()

        if (pTitle.isEmpty()){
            etTitle.error = "Please enter job title"
        }
        if (pLocation.isEmpty()){
            etLocation.error = "Please enter location"
        }
        if (pAbout.isEmpty()){
            etAbout.error = "Please describe your service"
        }
        if (pContact.isEmpty()){
            etContact.error = "Please enter contact number"
        }

        val postId = dbRef.push().key!!

        val post = PostModel(postId,pTitle,pLocation,pAbout,pContact)

        dbRef.child(postId).setValue(post)
            .addOnCompleteListener{
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG ).show()

                etTitle.text.clear()
                etLocation.text.clear()
                etAbout.text.clear()
                etContact.text.clear()

            }.addOnFailureListener{err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }


    }
}