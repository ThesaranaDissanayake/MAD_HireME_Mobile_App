package com.example.hireme.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hireme.R
import com.example.hireme.models.UsersModel
import com.google.firebase.database.FirebaseDatabase

class UsersDetailsActivity : AppCompatActivity() {

    private lateinit var tvUserId: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserMobileNumber: TextView
    private lateinit var tvUserAddress: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employee data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvUserId = findViewById(R.id.tvUserId)
        tvUserName = findViewById(R.id.tvUserName)
        tvUserMobileNumber = findViewById(R.id.tvUserMobileNumber)
        tvUserAddress = findViewById(R.id.tvUser_Address)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvUserId.text = intent.getStringExtra("userId")
        tvUserName.text = intent.getStringExtra("userName")
        tvUserMobileNumber.text = intent.getStringExtra("userMobileNumber")
        tvUserAddress.text = intent.getStringExtra("userAddress")

    }

    private fun openUpdateDialog(
        userId: String,
        userName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etUserName = mDialogView.findViewById<EditText>(R.id.etUserName)
        val etUserMobileNumber = mDialogView.findViewById<EditText>(R.id.etUserMobileNumber)
        val etUserAddress = mDialogView.findViewById<EditText>(R.id.etUserAddress)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etUserName.setText(intent.getStringExtra("userName").toString())
        etUserMobileNumber.setText(intent.getStringExtra("userMobileNumber").toString())
        etUserAddress.setText(intent.getStringExtra("userAddress").toString())

        mDialog.setTitle("Updating $userName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateUserData(
                userId,
                etUserName.text.toString(),
                etUserMobileNumber.text.toString(),
                etUserAddress.text.toString()
            )
            Toast.makeText(applicationContext, "Employee data updated", Toast.LENGTH_LONG).show()

            tvUserName.text = etUserName.text.toString()
            tvUserMobileNumber.text = etUserMobileNumber.text.toString()
            tvUserAddress.text = etUserAddress.text.toString()

            alertDialog.dismiss()
        }

    }
    private fun updateUserData(
        id:String,
        name:String,
        mobilenumber:String,
        address:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val userInfo = UsersModel(id, name, mobilenumber, address)
        dbRef.setValue(userInfo)
    }
}