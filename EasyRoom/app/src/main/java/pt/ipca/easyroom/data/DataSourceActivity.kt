package pt.ipca.easyroom.data

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import pt.ipca.easyroom.model.Property
import pt.ipca.easyroom.model.User

class DataSourceActivity {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun addUser(user: User, uid: String) {
        val collectionName = when (user.userType) {
            "Owner" -> "owners"
            "Tenant" -> "tenants"
            else -> throw IllegalArgumentException("Invalid user type: ${user.userType}")
        }

        db.collection(collectionName)
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added with ID: $uid")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun addProperty(property: Property) {
        db.collection("properties")
            .document()
            .set(property)
            .addOnSuccessListener {
                Log.d(TAG, "Property added successfully.")
            }
            .addOnFailureListener { _ ->
                Log.w(TAG, "Error adding property.")
            }
    }
}


