package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.easyroom.R
import pt.ipca.easyroom.adapter.PropertyTenantAdapter
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.data.RoomIdCallback
import pt.ipca.easyroom.data.TenantCallback
import pt.ipca.easyroom.model.Tenant

class PropertyTenantsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_tenants)

        val dataSourceActivity = DataSourceActivity(this)

        val recyclerView = findViewById<RecyclerView>(R.id.rvPropertyTenants)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val propertyId = intent.getStringExtra("PROPERTY_ID")
        if (propertyId != null) {
            Log.d("PropertyTenantsActivity", "Property ID is not null: $propertyId")
            dataSourceActivity.getRoomIdsByProperty(propertyId, object : RoomIdCallback {
                override fun onCallback(value: List<String>) {
                    Log.d("PropertyTenantsActivity", "Room IDs returned: $value")
                    dataSourceActivity.getTenantsByRoomIds(value, object : TenantCallback {
                        override fun onCallback(value: List<Tenant>) {
                            Log.d("PropertyTenantsActivity", "Number of tenants returned: ${value.size}")
                            val adapter = PropertyTenantAdapter(value, dataSourceActivity, this@PropertyTenantsActivity, propertyId)
                            recyclerView.adapter = adapter
                            Log.d("PropertyTenantsActivity", "Adapter set for RecyclerView")
                        }
                    })
                }
            })
        } else {
            Log.d("PropertyTenantsActivity", "Property ID is null")
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






