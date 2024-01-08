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
import pt.ipca.easyroom.screen.PropertyTenantsActivity
import pt.ipca.easyroom.screen.TenantInformationActivity

class PropertyTenantAdapter(private val tenants: List<Tenant>, private val dataSource: DataSourceActivity, private val context: Context, private val roomId: String) : RecyclerView.Adapter<PropertyTenantAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_property_tenant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tenant = tenants[position]

        val tvPropertyTenantName = holder.view.findViewById<TextView>(R.id.tvPropertyTenantName)
        tvPropertyTenantName.text = context.getString(R.string.tenant_name_concatenation, tenant.firstName, tenant.lastName)

        val tvPropertyTenantInformation = holder.view.findViewById<ImageView>(R.id.ivPropertyTenantInformation)
        tvPropertyTenantInformation.setOnClickListener {
            val intent = Intent(context, TenantInformationActivity::class.java)
            intent.putExtra("TENANT_ID", tenant.id)
            context.startActivity(intent)
        }


        val ivExcludeFromProperty = holder.view.findViewById<ImageView>(R.id.ivExcludeFromProperty)
        ivExcludeFromProperty.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Exclude Tenant")
                .setMessage("Are you sure you want to exclude this tenant from the room?")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    dataSource.excludeTenantFromRoom(tenant.id)
                    val intent = Intent(context, PropertyTenantsActivity::class.java)
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
