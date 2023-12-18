package pt.ipca.easyroom

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val tvFirstName = findViewById<TextView>(R.id.tvFirstName)
        val tvLastName = findViewById<TextView>(R.id.tvLastName)
        val tvEmailAddress = findViewById<TextView>(R.id.tvEmailAddress)
        val tvPhoneNumber = findViewById<TextView>(R.id.tvPhoneNumber)
        val tvUserType = findViewById<TextView>(R.id.tvUserType)

        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        tvFirstName.text = document.getString("firstName")
                        tvLastName.text = document.getString("lastName")
                        tvEmailAddress.text = document.getString("email")
                        tvPhoneNumber.text = document.getString("phoneNumber")
                        tvUserType.text = document.getString("userType")
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Document not found.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
                .addOnFailureListener { _ ->
                    Toast.makeText(
                        baseContext,
                        "Error recovering data.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
        } else {
            Toast.makeText(
                baseContext,
                "No current user",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}
