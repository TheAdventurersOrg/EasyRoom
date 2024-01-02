package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.easyroom.R
import pt.ipca.easyroom.adapter.RoomAdapter
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.data.RoomCallback
import pt.ipca.easyroom.model.Room

class RoomsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        val dataSourceActivity = DataSourceActivity(this)

        val recyclerView = findViewById<RecyclerView>(R.id.rvRooms)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val propertyId = intent.getStringExtra("PROPERTY_ID")
        if (propertyId != null) {
            recyclerView.adapter = RoomAdapter(emptyList(), dataSourceActivity, this@RoomsActivity, propertyId)

            Log.d("RoomsActivity", "Getting rooms for property ID: $propertyId")
            dataSourceActivity.getRoomsByProperty(propertyId, object : RoomCallback {
                override fun onCallback(value: List<Room>) {
                    Log.d("RoomsActivity", "Number of rooms returned: ${value.size}")
                    val adapter = RoomAdapter(value, dataSourceActivity, this@RoomsActivity, propertyId)
                    recyclerView.adapter = adapter
                }
            })
        }

        val ivLogOut = findViewById<ImageView>(R.id.ivLogOut)
        ivLogOut.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val ivHome = findViewById<ImageView>(R.id.ivHome)
        ivHome.setOnClickListener {
            val intent = Intent(this, HomeOwnerActivity::class.java)
            startActivity(intent)
        }

        val ivAddRoom = findViewById<ImageView>(R.id.ivAddRoom)
        ivAddRoom.setOnClickListener {
            val intent = Intent(this, AddRoomActivity::class.java)
            intent.putExtra("PROPERTY_ID", propertyId)
            Log.d("AddRoomActivity", "Property ID: $propertyId")
            startActivity(intent)
        }

    }
}
