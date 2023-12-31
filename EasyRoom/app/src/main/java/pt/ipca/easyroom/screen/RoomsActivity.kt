package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.easyroom.R
import pt.ipca.easyroom.adapter.RoomAdapter
import pt.ipca.easyroom.data.DataSourceActivity

class RoomsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        val dataSourceActivity = DataSourceActivity(this)

        val recyclerView = findViewById<RecyclerView>(R.id.rvRooms)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RoomAdapter(emptyList(), dataSourceActivity, this@RoomsActivity)

        val dataSource = DataSourceActivity(this)
        val propertyId = FirebaseAuth.getInstance().currentUser?.uid
        if (propertyId != null) {
            dataSource.getRoomsByProperty(propertyId) { rooms ->
                val adapter = RoomAdapter(rooms, dataSourceActivity, this@RoomsActivity)
                recyclerView.adapter = adapter
            }
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
            startActivity(intent)
        }
    }
}
