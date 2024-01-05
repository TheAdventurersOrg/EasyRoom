package pt.ipca.easyroom.adapter

import android.app.AlertDialog
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
import pt.ipca.easyroom.screen.RoomActivity

class RoomTenantAdapter(private val tenants: List<Tenant>, private val dataSource: DataSourceActivity, private val context: Context, private val roomId: String) : RecyclerView.Adapter<RoomTenantAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_room_tenant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tenant = tenants[position]

        val tvRoomTenantName = holder.view.findViewById<TextView>(R.id.tvRoomTenantName)
        val tenantName = context.getString(R.string.tenant_name, tenant.firstName, tenant.lastName)
        tvRoomTenantName.text = tenantName

        val ivExcludeFromRoom = holder.view.findViewById<ImageView>(R.id.ivExcludeFromRoom)
        ivExcludeFromRoom.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Exclude Tenant")
                .setMessage("Are you sure you want to exclude this tenant from the room?")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    dataSource.excludeTenantFromRoom(tenant.id)
                    val intent = Intent(context, RoomActivity::class.java)
                    intent.putExtra("ROOM_ID", roomId)
                    context.startActivity(intent)
                    Toast.makeText(context, "Tenant excluded successfully.", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(R.drawable.ic_dialog_alert)
                .show()
        }
    }

    override fun getItemCount() = tenants.size
}
