package pt.ipca.easyroom.screen

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import pt.ipca.easyroom.R

class HomeTenantActivity : AppCompatActivity() {
    private lateinit var tenantId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tenant)

        tenantId = intent.getStringExtra("TENANT_ID") ?: ""

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
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

        val ivNotificationsTenant = findViewById<ImageView>(R.id.ivNotificationsTenant)
        ivNotificationsTenant.setOnClickListener {
            val intent = Intent(this, NotificationsTenantActivity::class.java)
            startActivity(intent)
        }

        val ivTask = findViewById<ImageView>(R.id.ivTask)
        ivTask.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
        }

        val ivOtherTenants = findViewById<ImageView>(R.id.ivOtherTenants)
        ivOtherTenants.setOnClickListener {
            if (tenantId.isBlank()) {
                Log.d(TAG, "Error: tenantId is null or blank")
            } else {
                val intent = Intent(this, OtherTenantsActivity::class.java)
                intent.putExtra("TENANT_ID", tenantId)
                startActivity(intent)
            }
        }

        val ivOwner = findViewById<ImageView>(R.id.ivOwner)
        ivOwner.setOnClickListener {
            val intent = Intent(this, OwnerOfThePropertyActivity::class.java)
            intent.putExtra("TENANT_ID", tenantId)
            startActivity(intent)
        }
    }
}