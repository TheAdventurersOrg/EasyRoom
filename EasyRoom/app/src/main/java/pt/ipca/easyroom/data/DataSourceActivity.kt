package pt.ipca.easyroom.data

import android.util.Log
import android.content.ContentValues.TAG
import com.google.firebase.firestore.FirebaseFirestore
import pt.ipca.easyroom.model.User

class DataSourceActivity {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun createUser(firstName: String, lastName: String, email: String, phoneNumber: String, userType: String): User {
        return User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            userType = userType
        )
    }

    fun addUser(user: User, uid: String) {
        db.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added with ID: $uid")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}

