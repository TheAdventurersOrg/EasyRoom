package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pt.ipca.easyroom.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmailAddress = findViewById<EditText>(R.id.etEmailAddress)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btSignIn = findViewById<Button>(R.id.btSignIn)
        val btCreateAccount = findViewById<Button>(R.id.btCreateAccount)

        auth = FirebaseAuth.getInstance()

        btSignIn.setOnClickListener {
            val email = etEmailAddress.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() && password.isEmpty()){
                Toast.makeText(this, "Please fill the fields.", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null) {
                                val db = FirebaseFirestore.getInstance()
                                val ownerRef = db.collection("owners").document(user.uid)
                                val tenantRef = db.collection("tenants").document(user.uid)

                                ownerRef.get().addOnSuccessListener { ownerDocument ->
                                    if (ownerDocument.exists()) {
                                        val intent = Intent(this, HomeOwnerActivity::class.java)
                                        startActivity(intent)
                                        Toast.makeText(baseContext, "Authentication success.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        tenantRef.get().addOnSuccessListener { tenantDocument ->
                                            if (tenantDocument.exists()) {
                                                val intent = Intent(this, HomeTenantActivity::class.java)
                                                startActivity(intent)
                                                Toast.makeText(baseContext, "Authentication success.", Toast.LENGTH_SHORT).show()
                                            } else {
                                                Toast.makeText(baseContext, "Unknown user type.", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }.addOnFailureListener { _ ->
                                    Toast.makeText(baseContext, "Failed to get user type.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        btCreateAccount.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
