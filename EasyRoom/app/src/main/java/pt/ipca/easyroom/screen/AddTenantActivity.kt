package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.easyroom.R
import pt.ipca.easyroom.adapter.AddTenantAdapter
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.data.TenantCallback
import pt.ipca.easyroom.model.Tenant

class AddTenantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tenant)

        val dataSourceActivity = DataSourceActivity(this)

        val recyclerView = findViewById<RecyclerView>(R.id.rvAddTenant)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val roomId = intent.getStringExtra("ROOM_ID")
        Log.d("AddTenantActivity", "Received roomId from RoomTenantsActivity: $roomId")
        if (roomId != null) {
            Log.d("AddTenantActivity", "Getting all tenants without room")
            dataSourceActivity.getTenantsWithoutRoom(object : TenantCallback {
                override fun onCallback(value: List<Tenant>) {
                    Log.d("AddTenantActivity", "Number of tenants returned: ${value.size}")
                    val adapter = AddTenantAdapter(value, dataSourceActivity, this@AddTenantActivity, roomId)
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
    }
}

