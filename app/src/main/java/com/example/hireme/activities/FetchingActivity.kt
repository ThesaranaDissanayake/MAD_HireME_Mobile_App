package com.example.hireme.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hireme.adapters.UserAdapter
import com.example.hireme.models.UsersModel
import com.example.hireme.R
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var userList: ArrayList<UsersModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        userRecyclerView = findViewById(R.id.rvUser)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        userList = arrayListOf<UsersModel>()

        getUsersData()

    }

    private fun getUsersData() {

        userRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if (snapshot.exists()){
                    for (userSnap in snapshot.children){
                        val userData = userSnap.getValue(UsersModel::class.java)
                        userList.add(userData!!)
                    }
                    val mAdapter = UserAdapter(userList)
                    userRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : UserAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, UsersDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("userId", userList[position].userId)
                            intent.putExtra("userName", userList[position].userName)
                            intent.putExtra("userMobileNumber", userList[position].userMobileNumber)
                            intent.putExtra("userAddress", userList[position].userAddress)
                            startActivity(intent)
                        }

                    })

                    userRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}