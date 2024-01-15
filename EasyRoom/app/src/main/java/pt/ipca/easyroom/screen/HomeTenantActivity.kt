package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pt.ipca.easyroom.R
import pt.ipca.easyroom.model.Room
import pt.ipca.easyroom.model.Tenant

class HomeTenantActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tenant)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val tenantId = auth.currentUser?.uid ?: ""

        db.collection("tenants").document(tenantId).get().addOnSuccessListener { document ->
            val tenant = document.toObject(Tenant::class.java)
            val roomId = tenant?.roomId

            val ivLogOut = findViewById<ImageView>(R.id.ivLogOut)
            ivLogOut.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            val ivProfile = findViewById<ImageView>(R.id.ivProfile)
            ivProfile.setOnClickListener {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }

            val ivPayment = findViewById<ImageView>(R.id.ivPayment)
            ivPayment.setOnClickListener {
                if (roomId != "none") {
                    val intent = Intent(this, PaymentActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Tenant is not yet linked to a room.", Toast.LENGTH_SHORT).show()
                }
            }

            val ivNotificationsTenant = findViewById<ImageView>(R.id.ivNotificationsTenant)
            ivNotificationsTenant.setOnClickListener {
                if (roomId != "none") {
                    val intent = Intent(this, NotificationsTenantActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Tenant is not yet linked to a room.", Toast.LENGTH_SHORT).show()
                }
            }

            val ivRoomChatWithOwner = findViewById<ImageView>(R.id.ivRoomChatWithOwner)
            ivRoomChatWithOwner.setOnClickListener {
                roomId?.let { id ->
                    if (id != "none") {
                        db.collection("rooms").document(id).get().addOnSuccessListener { document ->
                            val room = document.toObject(Room::class.java)
                            val roomName = room?.name
                            val intent = Intent(this, RoomChatWithOwnerActivity::class.java)
                            intent.putExtra("ROOM_ID", id)
                            intent.putExtra("ROOM_NAME", roomName)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this, "Tenant is not yet linked to a room.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val ivOtherTenants = findViewById<ImageView>(R.id.ivOtherTenants)
            ivOtherTenants.setOnClickListener {
                if (roomId != "none") {
                    val intent = Intent(this, OtherTenantsActivity::class.java)
                    intent.putExtra("TENANT_ID", tenantId)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Tenant is not yet linked to a room.", Toast.LENGTH_SHORT).show()
                }
            }

            val ivOwnerProfile = findViewById<ImageView>(R.id.ivOwnerProfile)
            ivOwnerProfile.setOnClickListener {
                if (roomId != "none") {
                    val intent = Intent(this, OwnerProfileActivity::class.java)
                    intent.putExtra("TENANT_ID", tenantId)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Tenant is not yet linked to a room.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
