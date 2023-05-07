package com.example.hireme.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import android.widget.Toast
import com.example.hireme.models.UsersModel
import com.example.hireme.R


class InsertionActivity : AppCompatActivity() {

    private lateinit var etUserName: EditText
    private lateinit var etUserMobileNumber: EditText
    private lateinit var etUserAddress: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etUserName = findViewById(R.id.etUserName)
        etUserMobileNumber = findViewById(R.id.etUserMobileNumber)
        etUserAddress = findViewById(R.id.etUserAddress)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val userName = etUserName.text.toString()
        val userMobileNumber = etUserMobileNumber.text.toString()
        val userAddress = etUserAddress.text.toString()

        if (userName.isEmpty()) {
            etUserName.error = "Please enter name"
        }
        if (userMobileNumber.isEmpty()) {
            etUserMobileNumber.error = "Please enter mobile number"
        }
        if (userAddress.isEmpty()) {
            etUserAddress.error = "Please enter address"
        }

        val userId = dbRef.push().key!!

        val Users = UsersModel(userId, userName, userMobileNumber, userAddress)

        dbRef.child(userId).setValue(Users)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etUserName.text.clear()
                etUserMobileNumber.text.clear()
                etUserAddress.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}