package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.easyroom.R
import pt.ipca.easyroom.adapter.RoomTenantAdapter
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.data.TenantCallback
import pt.ipca.easyroom.model.Tenant

class RoomTenantsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_tenants)

        val dataSourceActivity = DataSourceActivity(this)

        val recyclerView = findViewById<RecyclerView>(R.id.rvRoomTenants)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val roomId = intent.getStringExtra("ROOM_ID")
        if (roomId != null) {
            Log.d("RoomTenantsActivity", "Room ID is not null: $roomId")
            recyclerView.adapter = RoomTenantAdapter(emptyList(), dataSourceActivity, this@RoomTenantsActivity, roomId)
            Log.d("RoomTenantsActivity", "Getting tenants for room ID: $roomId")
            dataSourceActivity.getTenantsByRoom(roomId, object : TenantCallback {
                override fun onCallback(value: List<Tenant>) {
                    Log.d("RoomTenantsActivity", "Number of tenants returned: ${value.size}")
                        val adapter = RoomTenantAdapter(value, dataSourceActivity, this@RoomTenantsActivity, roomId)
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

        val ivAddTenant = findViewById<ImageView>(R.id.ivAddTenant)
        ivAddTenant.setOnClickListener {
            val intent = Intent(this, AddTenantActivity::class.java)
            intent.putExtra("ROOM_ID", roomId)
            Log.d("RoomTenantsActivity", "Passing roomId to AddTenantActivity: $roomId")
            startActivity(intent)
        }
    }
}


