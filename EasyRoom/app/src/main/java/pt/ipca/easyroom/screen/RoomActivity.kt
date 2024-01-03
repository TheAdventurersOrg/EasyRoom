package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import pt.ipca.easyroom.R

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val tvRoom = findViewById<TextView>(R.id.tvRoom)
        val roomName = intent.getStringExtra("ROOM_NAME")
        tvRoom.text = roomName

        val ivRoomPayments = findViewById<ImageView>(R.id.ivRoomPayments)
        ivRoomPayments.setOnClickListener {
            val intent = Intent(this, RoomPaymentsActivity::class.java)
            startActivity(intent)
        }

        val ivRoomNotifications = findViewById<ImageView>(R.id.ivRoomNotifications)
        ivRoomNotifications.setOnClickListener {
            val intent = Intent(this, RoomNotificationsActivity::class.java)
            startActivity(intent)
        }

        val ivRoomChat = findViewById<ImageView>(R.id.ivRoomChat)
        ivRoomChat.setOnClickListener {
            val intent = Intent(this, RoomChatActivity::class.java)
            startActivity(intent)
        }

        val ivRoomTenants = findViewById<ImageView>(R.id.ivRoomTenants)
        ivRoomTenants.setOnClickListener {
            val intent = Intent(this, RoomTenantsActivity::class.java)
            startActivity(intent)
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
    }
}
