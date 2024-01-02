package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import pt.ipca.easyroom.R
import pt.ipca.easyroom.model.Room
import pt.ipca.easyroom.data.DataSourceActivity

class AddRoomActivity : AppCompatActivity() {
    private lateinit var dataSourceActivity: DataSourceActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)

        val btToCreate = findViewById<Button>(R.id.btToCreate)

        dataSourceActivity = DataSourceActivity(this)

        btToCreate.setOnClickListener {
            val roomName = findViewById<EditText>(R.id.etRoomName).text.toString()
            val roomDescription = findViewById<EditText>(R.id.etRoomDescription).text.toString()
            val propertyId = intent.getStringExtra("PROPERTY_ID") ?: ""

            if (roomName.isBlank() || roomDescription.isBlank()) {
                Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
            } else {
                val newRoomId = dataSourceActivity.generateNewRoomId()
                val newRoom = Room(newRoomId, roomName, roomDescription, propertyId)
                Log.d("AddRoomActivity", "New Room's Property ID: ${newRoom.propertyId}")
                dataSourceActivity.addRoom(newRoom)
                Toast.makeText(this, "Room added successfully.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, PropertyActivity::class.java)
                intent.putExtra("PROPERTY_ID", propertyId)
                startActivity(intent)
                finish()
            }
        }
    }
}
