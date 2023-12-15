package pt.ipca.easyroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etEmailAddress = findViewById<EditText>(R.id.etEmailAddress)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btSignIn = findViewById<Button>(R.id.btSignIn)
        val btCreateAccount = findViewById<Button>(R.id.btCreateAccount)

        auth = FirebaseAuth.getInstance()

        btSignIn.setOnClickListener {
            val email = etEmailAddress.text.toString()
            val password = etPassword.text.toString()

            if (email.isNullOrBlank() && password.isNullOrBlank()){
                Toast.makeText(this, "Please fill the fields.", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(
                                baseContext,
                                "Authentication success.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
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