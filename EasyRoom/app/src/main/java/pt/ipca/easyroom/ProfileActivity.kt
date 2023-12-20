package pt.ipca.easyroom

import android.graphics.Typeface
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
                        tvFirstName.textSize = 20f
                        tvFirstName.setTypeface(null, Typeface.BOLD)

                        tvLastName.text = document.getString("lastName")
                        tvLastName.textSize = 20f
                        tvLastName.setTypeface(null, Typeface.BOLD)

                        tvEmailAddress.text = document.getString("email")
                        tvEmailAddress.textSize = 20f
                        tvEmailAddress.setTypeface(null, Typeface.BOLD)

                        tvPhoneNumber.text = document.getString("phoneNumber")
                        tvPhoneNumber.textSize = 20f
                        tvPhoneNumber.setTypeface(null, Typeface.BOLD)

                        tvUserType.text = document.getString("userType")
                        tvUserType.textSize = 20f
                        tvUserType.setTypeface(null, Typeface.BOLD)
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
