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
import pt.ipca.easyroom.adapter.GroupChatAdapter
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Message

class GroupChatActivity : AppCompatActivity() {
    private lateinit var rvChat: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btSend: ImageView
    private lateinit var dataSource: DataSourceActivity
    private lateinit var groupChatAdapter: GroupChatAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_chat)

        rvChat = findViewById(R.id.rvGroupChat)
        etMessage = findViewById(R.id.etMessage)
        btSend = findViewById(R.id.btSend)
        dataSource = DataSourceActivity(this)
        rvChat.layoutManager = LinearLayoutManager(this)
        groupChatAdapter = GroupChatAdapter(mutableListOf())
        rvChat.adapter = groupChatAdapter
        auth = FirebaseAuth.getInstance()

        val propertyId = intent.getStringExtra("PROPERTY_ID") ?: ""
        val tenantId = auth.currentUser?.uid ?: ""

        val tvGroup = findViewById<TextView>(R.id.tvGroup)
        val propertyName = intent.getStringExtra("PROPERTY_NAME")
        tvGroup.text = propertyName

        btSend.setOnClickListener {
            val messageText = etMessage.text.toString()
            dataSource.getUserName(tenantId) { userName ->
                val message = Message(text = messageText, senderId = tenantId, senderName = userName, receiverId = propertyId)
                dataSource.sendPropertyMessage(propertyId, message)
                etMessage.text.clear()
            }
        }

        dataSource.getPropertyMessages(propertyId) { messages ->
            val oldSize = groupChatAdapter.messages.size
            groupChatAdapter.messages.clear()
            groupChatAdapter.messages.addAll(messages)
            val newSize = groupChatAdapter.messages.size
            for (i in oldSize until newSize) {
                groupChatAdapter.notifyItemInserted(i)
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
