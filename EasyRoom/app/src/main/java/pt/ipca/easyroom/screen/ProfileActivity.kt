package pt.ipca.easyroom.screen

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import pt.ipca.easyroom.R

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
            val ownerRef = db.collection("owners").document(user.uid)
            val tenantRef = db.collection("tenants").document(user.uid)

            ownerRef.get().addOnSuccessListener { ownerDocument ->
                if (ownerDocument.exists()) {
                    // User is an owner
                    displayUserInfo(ownerDocument, tvFirstName, tvLastName, tvEmailAddress, tvPhoneNumber, tvUserType, "Owner")
                } else {
                    // Check if user is a tenant
                    tenantRef.get().addOnSuccessListener { tenantDocument ->
                        if (tenantDocument.exists()) {
                            // User is a tenant
                            displayUserInfo(tenantDocument, tvFirstName, tvLastName, tvEmailAddress, tvPhoneNumber, tvUserType, "Tenant")
                        } else {
                            // User is neither an owner nor a tenant
                            Toast.makeText(baseContext, "Unknown user type.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }.addOnFailureListener { _ ->
                Toast.makeText(baseContext, "Failed to get user type.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(baseContext, "No current user", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayUserInfo(document: DocumentSnapshot, tvFirstName: TextView, tvLastName: TextView, tvEmailAddress: TextView, tvPhoneNumber: TextView, tvUserType: TextView, userType: String) {
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

        tvUserType.text = userType
        tvUserType.textSize = 20f
        tvUserType.setTypeface(null, Typeface.BOLD)
    }
}
