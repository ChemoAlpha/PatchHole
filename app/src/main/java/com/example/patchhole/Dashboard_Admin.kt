package com.example.patchhole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Dashboard_Admin : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{

    lateinit var dashboardhome :HomeFragment
    lateinit var mapsFragment: MapsFragment
    lateinit var  invite_Fragment: inviteFragment
    lateinit var myreport_Fragment: myreportFragment
    lateinit var about_Fragment: aboutFragment
    lateinit var peopleFragment: PeopleFragment
    lateinit var workersFragment: WorkersFragment
    //lateinit var admin_panel_fragment: adminPanelFragment

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard__admin)

        auth = FirebaseAuth.getInstance()
        toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        var db = FirebaseFirestore.getInstance()
        val email = intent.getStringExtra("email")
        val docRef = db.collection("User").document(email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name =document.getString("name")
                    val nv=navView.getHeaderView(0)
                    val textView = nv.findViewById<TextView>(R.id.titlemain)
                    textView.setText(name).toString()
                    val textView1 = nv.findViewById<TextView>(R.id.titlesub)
                    textView1.setText(email).toString()

                } else {

                }
            }
            .addOnFailureListener { exception ->



            }

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        dashboardhome = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout,dashboardhome)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.home -> {
                dashboardhome = HomeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,dashboardhome)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_map -> {
                mapsFragment = MapsFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,mapsFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_myreport -> {
                myreport_Fragment = myreportFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,myreport_Fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.admin_panel -> {
                /*admin_panel_fragment =adminPanelFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,admin_panel_fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                */
                val intent = Intent(baseContext,adminPanel::class.java)
                startActivity(intent)

            }
            R.id.nav_peopleRep -> {

                peopleFragment = PeopleFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,peopleFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_workersPanel -> {

                workersFragment = WorkersFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,workersFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_invite -> {

                invite_Fragment = inviteFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,invite_Fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }

            R.id.nav_about -> {
                about_Fragment =aboutFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,about_Fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }


            R.id.nav_logout -> {
                auth.signOut()
                startActivity(Intent(this,MainActivity::class.java))
            }
            /*
            R.id.titlemain ->
            {

            }*/
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    ////////////////////back press acitvity/////////////////////////
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }




}