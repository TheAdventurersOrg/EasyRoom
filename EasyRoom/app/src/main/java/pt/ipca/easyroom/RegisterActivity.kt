package pt.ipca.easyroom

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etEmailAddress = findViewById<EditText>(R.id.etEmailAddress)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val etPhoneNumber = findViewById<EditText>(R.id.etPhoneNumber)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)
        val btRegister = findViewById<Button>(R.id.btRegister)
        val rgUserType = findViewById<RadioGroup>(R.id.rgUserType)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        btRegister.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmailAddress.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val termsAccepted = cbTerms.isChecked

            if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() || phoneNumber.isBlank()) {
                Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
            } else if (!termsAccepted) {
                Toast.makeText(this, "Please accept the terms and services.", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            } else if (!password.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))) {
                Toast.makeText(this, "Password must have at least 8 characters, including an uppercase letter, a lowercase letter, a number, and a special character.", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { authTask ->
                        if (authTask.isSuccessful) {
                            val user = auth.currentUser

                            val userType: String = when (rgUserType.checkedRadioButtonId) {
                                R.id.rbOwner -> "Owner"
                                R.id.rbTenant -> "Tenant"
                                else -> ""
                            }

                            if (userType.isBlank()) {
                                Toast.makeText(
                                    this,
                                    "Please select a profile type.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val userMap = hashMapOf(
                                    "firstName" to firstName,
                                    "lastName" to lastName,
                                    "email" to email,
                                    "phoneNumber" to phoneNumber,
                                    "userType" to userType
                                )

                                db.collection("users")
                                    .document(user!!.uid)
                                    .set(userMap)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "DocumentSnapshot added with ID: ${user.uid}")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                    }

                                Toast.makeText(
                                    baseContext,
                                    "Registration successful.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            if (authTask.exception is FirebaseAuthUserCollisionException) {
                                Toast.makeText(this, "Email is already in use.", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(baseContext, "Registration failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }
    }
}
