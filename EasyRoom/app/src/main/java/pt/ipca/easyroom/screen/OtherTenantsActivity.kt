package pt.ipca.easyroom.screen

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.easyroom.R
import pt.ipca.easyroom.adapter.OtherTenantAdapter
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.data.OtherTenantCallback
import pt.ipca.easyroom.model.Tenant

class OtherTenantsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_tenants)

        val dataSource = DataSourceActivity(this)

        val tenantId = intent.getStringExtra("TENANT_ID") ?: ""
        if (tenantId.isBlank()) {
            Log.d(TAG, "Error: tenantId is null or blank")
            return
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvOtherTenants)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = OtherTenantAdapter(emptyList(), dataSource, this@OtherTenantsActivity)

        dataSource.getOtherTenants(tenantId, object : OtherTenantCallback {
            override fun onCallback(value: List<Tenant>?, propertyId: String?) {
                if (!value.isNullOrEmpty()) {
                    val adapter = OtherTenantAdapter(value, dataSource, this@OtherTenantsActivity)
                    recyclerView.adapter = adapter
                }
            }
        })

        val ivLogOut = findViewById<ImageView>(R.id.ivLogOut)
        ivLogOut.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val ivHome = findViewById<ImageView>(R.id.ivHome)
        ivHome.setOnClickListener {
            val intent = Intent(this, HomeTenantActivity::class.java)
            intent.putExtra("TENANT_ID", tenantId)
            startActivity(intent)
        }

        val ivGroupChat = findViewById<ImageView>(R.id.ivGroupChat)
        ivGroupChat.setOnClickListener {
            val intent = Intent(this, GroupChatActivity::class.java)
            startActivity(intent)
        }
    }
}


