package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.easyroom.R
import pt.ipca.easyroom.adapter.PropertyAdapter
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Property
import pt.ipca.easyroom.data.PropertyCallback

class PropertiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_properties)

        val dataSourceActivity = DataSourceActivity(this)

        val recyclerView = findViewById<RecyclerView>(R.id.rvProperties)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PropertyAdapter(emptyList(), dataSourceActivity, this@PropertiesActivity)

        val dataSource = DataSourceActivity(this)
        val ownerId = FirebaseAuth.getInstance().currentUser?.uid
        if (ownerId != null) {
            dataSource.getPropertiesByOwner(ownerId, object : PropertyCallback {
                override fun onCallback(value: List<Property>) {
                    val adapter = PropertyAdapter(value, dataSourceActivity, this@PropertiesActivity)
                    recyclerView.adapter = adapter
                }
            })
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

        val ivAddProperty = findViewById<ImageView>(R.id.ivAddProperty)
        ivAddProperty.setOnClickListener {
            val intent = Intent(this, AddPropertyActivity::class.java)
            startActivity(intent)
        }
    }
}
