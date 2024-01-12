package pt.ipca.easyroom.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.easyroom.R
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Tenant
import pt.ipca.easyroom.screen.OtherTenantChatActivity
import pt.ipca.easyroom.screen.OtherTenantInformationActivity

class OtherTenantAdapter(private val tenants: List<Tenant>, private val dataSource: DataSourceActivity, private val context: Context) : RecyclerView.Adapter<OtherTenantAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_other_tenants, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tenant = tenants[position]

        val tvOtherTenantName = holder.view.findViewById<TextView>(R.id.tvOtherTenantName)
        tvOtherTenantName.text = context.getString(R.string.other_tenant_concatenation, tenant.firstName, tenant.lastName)

        val ivOtherTenantInformation = holder.view.findViewById<ImageView>(R.id.ivOtherTenantInformation)
        ivOtherTenantInformation.setOnClickListener {
            val intent = Intent(context, OtherTenantInformationActivity::class.java)
            context.startActivity(intent)
        }

        val ivOtherTenantChat = holder.view.findViewById<ImageView>(R.id.ivOtherTenantChat)
        ivOtherTenantChat.setOnClickListener {
            val intent = Intent(context, OtherTenantChatActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = tenants.size
}
