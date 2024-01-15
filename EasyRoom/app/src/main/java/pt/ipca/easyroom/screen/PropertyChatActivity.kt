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
import pt.ipca.easyroom.adapter.PropertyChatAdapter
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Message

class PropertyChatActivity : AppCompatActivity() {
    private lateinit var rvChat: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btSend: ImageView
    private lateinit var dataSource: DataSourceActivity
    private lateinit var propertyChatAdapter: PropertyChatAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_chat)

        rvChat = findViewById(R.id.rvPropertyChat)
        etMessage = findViewById(R.id.etMessage)
        btSend = findViewById(R.id.btSend)
        dataSource = DataSourceActivity(this)
        rvChat.layoutManager = LinearLayoutManager(this)
        propertyChatAdapter = PropertyChatAdapter(mutableListOf())
        rvChat.adapter = propertyChatAdapter
        auth = FirebaseAuth.getInstance()

        val propertyId = intent.getStringExtra("PROPERTY_ID") ?: ""
        val ownerId = auth.currentUser?.uid ?: ""

        val tvProperty = findViewById<TextView>(R.id.tvProperty)
        val propertyName = intent.getStringExtra("PROPERTY_NAME")
        tvProperty.text = propertyName

        btSend.setOnClickListener {
            val messageText = etMessage.text.toString()
            val dataSource = DataSourceActivity(this)
            dataSource.getUserName(ownerId) { userName ->
                val message = Message(text = messageText, senderId = ownerId, senderName = userName, receiverId = propertyId)
                dataSource.sendPropertyMessage(propertyId, message)
                etMessage.text.clear()
            }
        }

        dataSource.getPropertyMessages(propertyId) { messages ->
            val oldSize = propertyChatAdapter.messages.size
            propertyChatAdapter.messages.clear()
            propertyChatAdapter.messages.addAll(messages)
            val newSize = propertyChatAdapter.messages.size
            for (i in oldSize until newSize) {
                propertyChatAdapter.notifyItemInserted(i)
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
    }
}
