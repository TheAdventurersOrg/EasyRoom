package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import pt.ipca.easyroom.R

class PropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        val tvProperty = findViewById<TextView>(R.id.tvProperty)
        val propertyName = intent.getStringExtra("PROPERTY_NAME")
        tvProperty.text = propertyName

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