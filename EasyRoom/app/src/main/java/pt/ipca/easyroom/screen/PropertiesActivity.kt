package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import pt.ipca.easyroom.R

class PropertiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_properties)

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

        val ivMyProperties = findViewById<ImageView>(R.id.ivMyProperties)
        ivMyProperties.setOnClickListener {
            val intent = Intent(this, MyPropertiesActivity::class.java)
            startActivity(intent)
        }

        val ivAddProperty = findViewById<ImageView>(R.id.ivAddProperty)
        ivAddProperty.setOnClickListener {
            val intent = Intent(this, AddPropertyActivity::class.java)
            startActivity(intent)
        }

        val ivDeleteProperty = findViewById<ImageView>(R.id.ivDeleteProperty)
        ivDeleteProperty.setOnClickListener {
            val intent = Intent(this, DeletePropertyActivity::class.java)
            startActivity(intent)
        }

        val ivEditProperty = findViewById<ImageView>(R.id.ivEditProperty)
        ivEditProperty.setOnClickListener {
            val intent = Intent(this, EditPropertyActivity::class.java)
            startActivity(intent)
        }

    }
}