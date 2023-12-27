package pt.ipca.easyroom.data

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import pt.ipca.easyroom.model.Property
import pt.ipca.easyroom.model.User

interface PropertyCallback {
    fun onCallback(value: List<Property>)
}

class DataSourceActivity(private val context: Context) {
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
        val newPropertyRef = db.collection("properties").document()
        property.id = newPropertyRef.id
        newPropertyRef.set(property)
            .addOnSuccessListener {
                Log.d(TAG, "Property added successfully with ID: ${property.id}")
            }
            .addOnFailureListener { _ ->
                Log.w(TAG, "Error adding property.")
            }
    }

    fun getPropertiesByOwner(ownerId: String, callback: PropertyCallback) {
        db.collection("properties")
            .whereEqualTo("ownerId", ownerId)
            .get()
            .addOnSuccessListener { documents ->
                val properties = ArrayList<Property>()
                for (document in documents) {
                    val property = document.toObject(Property::class.java)
                    properties.add(property)
                }
                callback.onCallback(properties)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun updateProperty(propertyId: String, updatedProperty: Property): Task<Void> {
        return db.collection("properties")
            .document(propertyId)
            .set(updatedProperty)
            .addOnSuccessListener {
                Log.d(TAG, "Property updated successfully.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating property.", e)
            }
    }

    fun getPropertyById(propertyId: String, callback: (Property?) -> Unit) {
        db.collection("properties")
            .document(propertyId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val property = document.toObject(Property::class.java)
                    callback(property)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun deleteProperty(propertyId: String) {
        Toast.makeText(context, "Deleting property with ID: $propertyId", Toast.LENGTH_SHORT).show()
        db.collection("properties")
            .document(propertyId)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Property deleted successfully.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting property.", e)
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Property deleted successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to delete property.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun generateNewPropertyId(): String {
        return db.collection("properties").document().id
    }
}


