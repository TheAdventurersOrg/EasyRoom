package pt.ipca.easyroom.screen

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.easyroom.R
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Room

class RoomInformationActivity : AppCompatActivity() {
    private lateinit var roomId: String
    private lateinit var dataSource: DataSourceActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_information)

        roomId = intent.getStringExtra("ROOM_ID") ?: ""
        dataSource = DataSourceActivity(this)

        val tvRoomName = findViewById<TextView>(R.id.tvNameRoom)
        val tvRoomDescription = findViewById<TextView>(R.id.tvRoomDescription)

        dataSource.getRoomById(roomId) { room ->
            displayRoomInfo(room, tvRoomName, tvRoomDescription)
        }
    }

    private fun displayRoomInfo(room: Room?, tvRoomName: TextView, tvRoomDescription: TextView) {
        tvRoomName.text = room?.name
        tvRoomName.textSize = 20f
        tvRoomName.setTypeface(null, Typeface.BOLD)

        tvRoomDescription.text = room?.description
        tvRoomDescription.textSize = 20f
        tvRoomDescription.setTypeface(null, Typeface.BOLD)
    }
}
