package pt.ipca.easyroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HomeOwnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_owner)

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

        val ivProperties = findViewById<ImageView>(R.id.ivProperties)
        ivProperties.setOnClickListener {
            val intent = Intent(this, PropertiesActivity::class.java)
            startActivity(intent)
        }

        val ivNotifications = findViewById<ImageView>(R.id.ivNotifications)
        ivNotifications.setOnClickListener {
            val intent = Intent(this, NotificationsOwnerActivity::class.java)
            startActivity(intent)
        }
    }
}
