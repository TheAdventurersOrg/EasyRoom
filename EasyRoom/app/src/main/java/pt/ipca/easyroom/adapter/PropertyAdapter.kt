package pt.ipca.easyroom.adapter

import android.app.AlertDialog
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
import pt.ipca.easyroom.model.Property
import pt.ipca.easyroom.screen.UpdatePropertyActivity
import pt.ipca.easyroom.screen.PropertiesActivity
import pt.ipca.easyroom.screen.PropertyActivity
import pt.ipca.easyroom.screen.PropertyInformationActivity

class PropertyAdapter(private val properties: List<Property>, private val dataSource: DataSourceActivity, private val context: Context) : RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_property, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = properties[position]

        val tvPropertyName = holder.view.findViewById<TextView>(R.id.tvPropertyName)
        tvPropertyName.text = property.name

        tvPropertyName.setOnClickListener {
            val intent = Intent(context, PropertyActivity::class.java)
            intent.putExtra("PROPERTY_NAME", property.name)
            intent.putExtra("PROPERTY_ID", property.id)
            context.startActivity(intent)
        }

        val ivPropertyInformation = holder.view.findViewById<ImageView>(R.id.ivPropertyInformation)
        ivPropertyInformation.setOnClickListener {
            val intent = Intent(context, PropertyInformationActivity::class.java)
            intent.putExtra("PROPERTY_ID", property.id)
            context.startActivity(intent)
        }

        val ivUpdateProperty = holder.view.findViewById<ImageView>(R.id.ivUpdateProperty)
        ivUpdateProperty.setOnClickListener {
            val intent = Intent(context, UpdatePropertyActivity::class.java)
            intent.putExtra("PROPERTY_ID", property.id)
            context.startActivity(intent)
        }

        val ivDeleteProperty = holder.view.findViewById<ImageView>(R.id.ivDeleteProperty)
        ivDeleteProperty.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete Property")
                .setMessage("Are you sure you want to delete this property?")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    dataSource.deleteProperty(property.id)
                    val intent = Intent(context, PropertiesActivity::class.java)
                    context.startActivity(intent)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(R.drawable.ic_dialog_alert)
                .show()
        }
    }

    override fun getItemCount() = properties.size
}

