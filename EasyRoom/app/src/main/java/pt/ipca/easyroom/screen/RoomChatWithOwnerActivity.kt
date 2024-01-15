package pt.ipca.easyroom.screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.easyroom.R
import pt.ipca.easyroom.adapter.RoomChatWithOwnerAdapter
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Message

class RoomChatWithOwnerActivity : AppCompatActivity() {
    private lateinit var rvChat: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btSend: ImageView
    private lateinit var dataSource: DataSourceActivity
    private lateinit var roomChatWithOwnerAdapter: RoomChatWithOwnerAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_chat_with_owner)

        rvChat = findViewById(R.id.rvRoomChat)
        etMessage = findViewById(R.id.etMessage)
        btSend = findViewById(R.id.btSend)
        dataSource = DataSourceActivity(this)
        rvChat.layoutManager = LinearLayoutManager(this)
        roomChatWithOwnerAdapter = RoomChatWithOwnerAdapter(mutableListOf())
        rvChat.adapter = roomChatWithOwnerAdapter
        auth = FirebaseAuth.getInstance()

        val roomId = intent.getStringExtra("ROOM_ID") ?: ""
        val tenantId = auth.currentUser?.uid ?: ""

        val tvRoom = findViewById<TextView>(R.id.tvRoom)
        val roomName = intent.getStringExtra("ROOM_NAME")
        tvRoom.text = roomName

        btSend.setOnClickListener {
            val messageText = etMessage.text.toString()
            val dataSource = DataSourceActivity(this)
            dataSource.getUserName(tenantId) { userName ->
                val message = Message(text = messageText, senderId = tenantId, senderName = userName, receiverId = roomId)
                dataSource.sendRoomMessage(roomId, message)
                etMessage.text.clear()
            }
        }

        dataSource.getRoomMessages(roomId) { messages ->
            val oldSize = roomChatWithOwnerAdapter.messages.size
            roomChatWithOwnerAdapter.messages.clear()
            roomChatWithOwnerAdapter.messages.addAll(messages)
            val newSize = roomChatWithOwnerAdapter.messages.size
            for (i in oldSize until newSize) {
                roomChatWithOwnerAdapter.notifyItemInserted(i)
            }
        }

        val ivLogOut = findViewById<ImageView>(R.id.ivLogOut)
        ivLogOut.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val ivHome = findViewById<ImageView>(R.id.ivHome)
        ivHome.setOnClickListener {
            val intent = Intent(this, HomeTenantActivity::class.java)
            startActivity(intent)
        }
    }
}
