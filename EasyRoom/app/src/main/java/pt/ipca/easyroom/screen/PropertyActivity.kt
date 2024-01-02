package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import pt.ipca.easyroom.R

class PropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        val propertyId = intent.getStringExtra("PROPERTY_ID")

        val tvProperty = findViewById<TextView>(R.id.tvProperty)
        val propertyName = intent.getStringExtra("PROPERTY_NAME")
        tvProperty.text = propertyName

        val ivPropertyPayments = findViewById<ImageView>(R.id.ivPropertyPayments)
        ivPropertyPayments.setOnClickListener {
            val intent = Intent(this, PropertyPaymentsActivity::class.java)
            startActivity(intent)
        }

        val ivPropertyNotifications = findViewById<ImageView>(R.id.ivPropertyNotifications)
        ivPropertyNotifications.setOnClickListener {
            val intent = Intent(this, PropertyNotificationsActivity::class.java)
            startActivity(intent)
        }

        val ivPropertyRooms = findViewById<ImageView>(R.id.ivPropertyRooms)
        ivPropertyRooms.setOnClickListener {
            val intent = Intent(this, RoomsActivity::class.java)
            intent.putExtra("PROPERTY_ID", propertyId)
            Log.d("PropertyActivity", "Property ID: $propertyId")
            startActivity(intent)
        }

        val ivPropertyTenants = findViewById<ImageView>(R.id.ivPropertyTenants)
        ivPropertyTenants.setOnClickListener {
            val intent = Intent(this, PropertyTenantsActivity::class.java)
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