package pt.ipca.easyroom.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.easyroom.R
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Tenant
import pt.ipca.easyroom.screen.AddTenantActivity

class AddTenantAdapter(private val tenants: List<Tenant>, private val dataSource: DataSourceActivity, private val context: Context, private val roomId: String) : RecyclerView.Adapter<AddTenantAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_add_tenant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tenant = tenants[position]

        val tvTenantName = holder.view.findViewById<TextView>(R.id.tvTenantName)
        val tenantName = context.getString(R.string.add_tenant_name_concatenation, tenant.firstName, tenant.lastName)
        tvTenantName.text = tenantName

        val ivAddTenantInRoom = holder.view.findViewById<ImageView>(R.id.ivAddTenantInRoom)
        ivAddTenantInRoom.setOnClickListener {
            if (tenant.roomId != "none") {
                Toast.makeText(context, "This tenant is already in a room.", Toast.LENGTH_SHORT).show()
            } else {
                tenant.roomId = roomId
                dataSource.updateTenant(tenant.id, tenant)
                val intent = Intent(context, AddTenantActivity::class.java)
                intent.putExtra("ROOM_ID", roomId)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = tenants.size
}
