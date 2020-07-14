package com.example.patchhole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        Login.setOnClickListener{
            doLogin()



        }
        sign.setOnClickListener()
        {
            doSign()
        }

    }

    fun doSign()
    {
        startActivity(Intent(this,Register_user::class.java))
    }
    fun doLogin()
    {
        if(Email.text.toString().isEmpty())
        {
            Email.error ="Please enter the Email"
            Email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email.text.toString()).matches())
        {
            Email.error ="please enter valid mail"
            Email.requestFocus()
            return
        }
        if(Password.text.toString().isEmpty())
        {
            Password.error ="Please enter the Password"
            Password.requestFocus()
            return
        }


        auth.signInWithEmailAndPassword(Email.text.toString(), Password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }

                // ...
            }
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }



   private fun updateUI(currentUser: FirebaseUser?)
    {
    if(currentUser !=null)
    {
        startActivity(Intent(this,DashBoard_user::class.java))
    }
        else
    {
        Toast.makeText(baseContext, "Authentication failed.",
            Toast.LENGTH_SHORT).show()
    }
    }
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


}