package com.example.patchhole

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patchhole.Adapter.CoAdminAdapter
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin_panel.*

class adminPanel : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    //val docRef = db.collection("User").document(email.toString())

    val Item_COUNT=21
    var total_item=0
    var last_visible_item=0

    lateinit var adapter: CoAdminAdapter
    var isLoading=false
    var isMaxData=false
    var last_node:String?=""
    var last_key:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        getLasKey()

        val layoutManager = LinearLayoutManager(this)
        coAdminListRecyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(coAdminListRecyclerView.context,layoutManager.orientation)
        coAdminListRecyclerView.addItemDecoration(dividerItemDecoration)

        adapter = CoAdminAdapter(this)
        coAdminListRecyclerView.adapter = adapter

        getCoadmin()

        coAdminListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                total_item = layoutManager.itemCount
                last_visible_item = layoutManager.findLastVisibleItemPosition()

                if(!isLoading && total_item <= last_visible_item+Item_COUNT)
                {
                    getCoadmin()
                    isLoading = true
                }
            }
        })
    }

    private fun getCoadmin() {
        if(!isMaxData)
        {
            val query: Query
            if(TextUtils.isEmpty(last_node))
                query = FirebaseDatabase.getInstance().reference
                    .child("CoAdmin")
                    .orderByKey()
                    .limitToFirst(Item_COUNT)
            else
                query = FirebaseDatabase.getInstance().reference
                    .child("CoAdmin")
                    .orderByKey()
                    .startAt(last_node)
                    .limitToFirst(Item_COUNT)

            query.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.hasChildren())
                    {
                        val CoAdminList = ArrayList<CoAdmin>()
                        for (snapshot in p0.children)
                            CoAdminList.add(snapshot.getValue(CoAdmin::class.java)!!)
                        last_node = CoAdminList[CoAdminList.size-1].Email

                        if(!last_node.equals(last_key))
                            CoAdminList.removeAt(CoAdminList.size-1)
                        else
                            last_node = "End"

                        adapter.addAll(CoAdminList)
                        isLoading = false
                    }
                    else
                    {
                        isLoading = false
                        isMaxData = true

                    }
                }
            })
        }
    }

    private fun getLasKey() {

        val get_last_key = FirebaseDatabase.getInstance().getReference()
            .child("CoAdmin")
            .orderByKey()
            .limitToLast(1)
        get_last_key.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(userSnapShot in p0.children)
                    last_key = userSnapShot.key
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId
        if(id==R.id.refresh)
        {
            isMaxData = false
            last_node = adapter.lastItemId
            adapter.removeLastItem()
            adapter.notifyDataSetChanged()
            getLasKey()
            getCoadmin()
        }

        return true
    }

}