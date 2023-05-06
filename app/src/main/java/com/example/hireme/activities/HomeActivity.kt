package com.example.hireme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hireme.R
import com.example.hireme.adapters.PostAdapter
import com.example.hireme.models.PostModel
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {

    private lateinit var postRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var postList: ArrayList<PostModel>
    private lateinit var dbRef: DatabaseReference

    private lateinit var btnReg : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        postRecyclerView = findViewById(R.id.rvPost)
        postRecyclerView.layoutManager = LinearLayoutManager(this)
        postRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        postList = arrayListOf<PostModel>()
        getPostsData()

        btnReg = findViewById(R.id.btnReg)

        btnReg.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getPostsData(){
        postRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("JobPostings")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                if (snapshot.exists()){
                    for(postSnap in snapshot.children){
                        val postData = postSnap.getValue(PostModel::class.java)
                        postList.add(postData!!)
                    }
                    val mAdapter = PostAdapter(postList)
                    postRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : PostAdapter.onItemClickListener{
                        override fun onItemClick(position: Int){
                            val intent = Intent(this@HomeActivity,PostDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("postId", postList[position].postId)
                            intent.putExtra("postTitle", postList[position].etTitle)
                            intent.putExtra("postLocation", postList[position].etLocation)
                            intent.putExtra("postAbout", postList[position].etAbout)
                            intent.putExtra("postContact", postList[position].etContact)
                            startActivity(intent)
                        }

                    })

                    postRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}