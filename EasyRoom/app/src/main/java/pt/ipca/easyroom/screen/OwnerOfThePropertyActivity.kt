package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import pt.ipca.easyroom.R

class OwnerOfThePropertyActivity : AppCompatActivity() {
    private lateinit var tenantId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_of_the_property)

        tenantId = intent.getStringExtra("TENANT_ID") ?: ""

        val ivLogOut = findViewById<ImageView>(R.id.ivLogOut)
        ivLogOut.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val ivHomeTenant = findViewById<ImageView>(R.id.ivHomeTenant)
        ivHomeTenant.setOnClickListener {
            val intent = Intent(this, HomeTenantActivity::class.java)
            startActivity(intent)
        }

        val ivChat = findViewById<ImageView>(R.id.ivChat)
        ivChat.setOnClickListener {
            val intent = Intent(this, ChatWithOwnerActivity::class.java)
            startActivity(intent)
        }

        val ivOwnerProfile = findViewById<ImageView>(R.id.ivOwnerProfile)
        ivOwnerProfile.setOnClickListener {
            val intent = Intent(this, OwnerProfileActivity::class.java)
            intent.putExtra("TENANT_ID", tenantId)
            startActivity(intent)
        }
    }
}