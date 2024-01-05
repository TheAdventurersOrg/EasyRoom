package pt.ipca.easyroom.data

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import pt.ipca.easyroom.model.Property
import pt.ipca.easyroom.model.Room
import pt.ipca.easyroom.model.Tenant
import pt.ipca.easyroom.model.User

interface PropertyCallback {
    fun onCallback(value: List<Property>)
}

interface RoomCallback {
    fun onCallback(value: List<Room>)
}

interface TenantCallback {
    fun onCallback(value: List<Tenant>)
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

    fun addRoom(room: Room) {
        val newRoomRef = db.collection("rooms").document()
        room.id = newRoomRef.id
        newRoomRef.set(room)
            .addOnSuccessListener {
                Log.d(TAG, "Room added successfully with ID: ${room.id}")
            }
            .addOnFailureListener { _ ->
                Log.w(TAG, "Error adding room.")
            }
    }

    fun getRoomsByProperty(propertyId: String, callback: RoomCallback) {
        db.collection("rooms")
            .whereEqualTo("propertyId", propertyId)
            .get()
            .addOnSuccessListener { documents ->
                val rooms = ArrayList<Room>()
                for (document in documents) {
                    val room = document.toObject(Room::class.java)
                    rooms.add(room)
                }
                callback.onCallback(rooms)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun updateRoom(roomId: String, updatedRoom: Room): Task<Void> {
        return db.collection("rooms")
            .document(roomId)
            .set(updatedRoom)
            .addOnSuccessListener {
                Log.d(TAG, "Room updated successfully.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating room.", e)
            }
    }

    fun getRoomById(roomId: String, callback: (Room?) -> Unit) {
        db.collection("rooms")
            .document(roomId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val room = document.toObject(Room::class.java)
                    callback(room)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun deleteRoom(roomId: String) {
        Toast.makeText(context, "Deleting room with ID: $roomId", Toast.LENGTH_SHORT).show()
        db.collection("rooms")
            .document(roomId)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Room deleted successfully.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting room.", e)
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Room deleted successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to delete room.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun generateNewRoomId(): String {
        return db.collection("rooms").document().id
    }

    fun getTenantsByRoom(roomId: String, callback: TenantCallback) {
        Log.d("DataSourceActivity", "Getting tenants for room ID: $roomId")
        db.collection("tenants")
            .whereEqualTo("roomId", roomId)
            .get()
            .addOnSuccessListener { documents ->
                val tenants = ArrayList<Tenant>()
                for (document in documents) {
                    val tenant = document.toObject(Tenant::class.java)
                    tenants.add(tenant)
                    Log.d("DataSourceActivity", "Tenant added: ${tenant.firstName} ${tenant.lastName}")
                }
                Log.d("DataSourceActivity", "Number of tenants returned: ${tenants.size}")
                callback.onCallback(tenants)
            }
            .addOnFailureListener { exception ->
                Log.w("DataSourceActivity", "Error getting documents: ", exception)
            }
    }

    fun excludeTenantFromRoom(tenantId: String) {
        db.collection("tenants")
            .document(tenantId)
            .update("roomId", null)
            .addOnSuccessListener {
                Log.d(TAG, "Tenant excluded from room successfully.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error excluding tenant from room.", e)
            }
    }

    fun getTenantsWithoutRoom(callback: TenantCallback) {
        db.collection("tenants")
            .whereEqualTo("roomId", "none")
            .get()
            .addOnSuccessListener { documents ->
                val tenants = ArrayList<Tenant>()
                for (document in documents) {
                    val tenant = document.toObject(Tenant::class.java)
                    tenants.add(tenant)
                }
                callback.onCallback(tenants)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun updateTenant(tenantId: String, updatedTenant: Tenant): Task<Void> {
        return db.collection("tenants")
            .document(tenantId)
            .set(updatedTenant)
            .addOnSuccessListener {
                Log.d(TAG, "Tenant updated successfully.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating tenant.", e)
            }
    }


}


