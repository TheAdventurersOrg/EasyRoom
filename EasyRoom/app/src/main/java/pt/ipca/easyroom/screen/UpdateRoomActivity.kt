package pt.ipca.easyroom.screen

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import pt.ipca.easyroom.R
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Room

class UpdateRoomActivity : AppCompatActivity() {
    private lateinit var roomId: String
    private lateinit var propertyId: String
    private lateinit var dataSource: DataSourceActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_room)

        roomId = intent.getStringExtra("ROOM_ID") ?: ""
        propertyId = intent.getStringExtra("PROPERTY_ID") ?: ""
        dataSource = DataSourceActivity(this)

        dataSource.getRoomById(roomId) { room ->
            findViewById<EditText>(R.id.etNameRoom).setText(room?.name)
            findViewById<EditText>(R.id.etDescriptionRoom).setText(room?.description)
        }

        findViewById<Button>(R.id.btSaveChanges).setOnClickListener {
            val roomName = findViewById<EditText>(R.id.etNameRoom).text.toString()
            val roomDescription = findViewById<EditText>(R.id.etDescriptionRoom).text.toString()

            if (roomName.isBlank() || roomDescription.isBlank()) {
                Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
            } else {
                val updatedRoom = Room(roomId, roomName, roomDescription, propertyId)
                dataSource.updateRoom(roomId, updatedRoom)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Room updated successfully.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, PropertyActivity::class.java)
                        intent.putExtra("PROPERTY_ID", propertyId)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to update room.", Toast.LENGTH_SHORT).show()
                        Log.w(TAG, "Error updating room.", e)
                    }
            }
        }
    }
}

