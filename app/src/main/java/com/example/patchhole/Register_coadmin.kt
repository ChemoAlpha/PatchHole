package com.example.patchhole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register_coadmin.*
import kotlinx.android.synthetic.main.activity_register_user.*
import kotlinx.android.synthetic.main.activity_register_user.EmailReg
import kotlinx.android.synthetic.main.activity_register_user.Name
import kotlinx.android.synthetic.main.activity_register_user.passwordReg
import kotlinx.android.synthetic.main.activity_register_user.progressBar

class Register_coadmin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_coadmin)

        auth = FirebaseAuth.getInstance()
        val btnCreate = findViewById<Button>(R.id.btnCreate)
        btnCreate.setOnClickListener{
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
    private  fun  doRegister() {
        if (EmailReg.text.toString().isEmpty()) {
            EmailReg.error = "Plaese enter the email"
            EmailReg.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(EmailReg.text.toString()).matches()) {
            EmailReg.error = "please enter valid mail"
            EmailReg.requestFocus()
            return
        }
        if (passwordReg.text.toString().isEmpty()) {
            passwordReg.error = "Please enter the Password"
            passwordReg.requestFocus()
            return
        }
        progressBar.visibility = View.VISIBLE
        var email = EmailReg.text.toString()
        var password = passwordReg.text.toString()
        var full_name = Name.text.toString()
        var subAdminArea = taluka.text.toString()

        val db = FirebaseFirestore.getInstance().document("CoAdmin/" + email)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    try {
                        val list1 = HashMap<String, Any>()
                        list1.put("name", full_name)
                        list1.put("email", email)
                        list1.put("password", password)
                        list1.put("subadminarea",subAdminArea)

                        db.set(list1).addOnSuccessListener { void: Void? ->
                            Toast.makeText(this, "suessfully registerd", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, DashBoard_user::class.java))
                            val intent = Intent(this, DashBoard_user::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()

                        }

                    } catch (e: Exception) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }


    }
}