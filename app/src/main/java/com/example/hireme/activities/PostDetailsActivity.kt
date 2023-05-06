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
import com.example.hireme.models.PostModel
import com.google.firebase.database.FirebaseDatabase

class PostDetailsActivity : AppCompatActivity() {

    private lateinit var tvPostId: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvAbout: TextView
    private lateinit var tvContact: TextView

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)


        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("postId").toString(),
                intent.getStringExtra("postTitle").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("postId").toString()
            )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("JobPostings").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employee data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, HomeActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvPostId = findViewById(R.id.tvPostId)
        tvTitle = findViewById(R.id.tvTitle)
        tvLocation = findViewById(R.id.tvLocation)
        tvAbout = findViewById(R.id.tvAbout)
        tvContact = findViewById(R.id.tvContact)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){
        tvPostId.text = intent.getStringExtra("postId")
        tvTitle.text = intent.getStringExtra("postTitle")
        tvLocation.text = intent.getStringExtra("postLocation")
        tvAbout.text = intent.getStringExtra("postAbout")
        tvContact.text = intent.getStringExtra("postContact")
    }

    private fun openUpdateDialog(
        postId: String,
        postTitle: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etTitle = mDialogView.findViewById<EditText>(R.id.etTitle)
        val etLocation = mDialogView.findViewById<EditText>(R.id.etLocation)
        val etAbout = mDialogView.findViewById<EditText>(R.id.etAbout)
        val etContact = mDialogView.findViewById<EditText>(R.id.etContact)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etTitle.setText(intent.getStringExtra("postTitle").toString())
        etLocation.setText(intent.getStringExtra("postLocation").toString())
        etAbout.setText(intent.getStringExtra("postAbout").toString())
        etContact.setText(intent.getStringExtra("postContact").toString())

        mDialog.setTitle("Updating $postTitle Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateEmpData(
                postId,
                etTitle.text.toString(),
                etLocation.text.toString(),
                etAbout.text.toString(),
                etContact.text.toString()
            )
            Toast.makeText(applicationContext, "Post data updated", Toast.LENGTH_LONG).show()

            tvTitle.text = etTitle.text.toString()
            tvLocation.text = etLocation.text.toString()
            tvAbout.text = etAbout.text.toString()
            tvContact.text = etContact.text.toString()

            alertDialog.dismiss()
        }
    }
    private fun updateEmpData(
        id:String,
        title:String,
        location:String,
        about:String,
        contact:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("JobPostings").child(id)
        val postInfo = PostModel(id, title, location, about, contact)
        dbRef.setValue(postInfo)
    }

}