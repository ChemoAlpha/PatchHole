package com.example.patchhole

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register_user.*

class Register_user : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        auth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener{
            doRegister()
        }


    }
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            auth.signOut()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
  private  fun  doRegister()
    {
      if(EmailReg.text.toString().isEmpty())
      {
          EmailReg.error="Plaese enter the email"
          EmailReg.requestFocus()
          return
      }
        if(!Patterns.EMAIL_ADDRESS.matcher(EmailReg.text.toString()).matches())
        {
            EmailReg.error ="please enter valid mail"
            EmailReg.requestFocus()
            return
        }
        if(passwordReg.text.toString().isEmpty())
        {
            passwordReg.error ="Please enter the Password"
            passwordReg.requestFocus()
            return
        }
        progressBar.visibility = View.VISIBLE
        var email=EmailReg.text.toString()
        var password=passwordReg.text.toString()
        var full_name=Name.text.toString()

        val db = FirebaseFirestore.getInstance().document("User/"+ email)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                {

                    try {
                        val list1 =HashMap<String, Any>()
                        list1.put("name",full_name)
                        list1.put("email",email)
                        list1.put("password",password)

                        db.set(list1).addOnSuccessListener {
                                void: Void? ->Toast.makeText(this,"suessfully registerd",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,DashBoard_user::class.java))
                            val intent=Intent(this,DashBoard_user::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)
                            finish()

                        }

                    }catch(e:Exception)
                    {
                        Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }









    }
}