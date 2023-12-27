package pt.ipca.easyroom.screen

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.easyroom.R
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Property

class PropertyInformationActivity : AppCompatActivity() {
    private lateinit var propertyId: String
    private lateinit var dataSource: DataSourceActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_information)

        propertyId = intent.getStringExtra("PROPERTY_ID") ?: ""
        dataSource = DataSourceActivity(this)

        val tvPropertyName = findViewById<TextView>(R.id.tvPropertyName)
        val tvPropertyAddress = findViewById<TextView>(R.id.tvPropertyAddress)
        val tvPropertyDescription = findViewById<TextView>(R.id.tvPropertyDescription)

        dataSource.getPropertyById(propertyId) { property ->
            displayPropertyInfo(property, tvPropertyName, tvPropertyAddress, tvPropertyDescription)
        }
    }

    private fun displayPropertyInfo(property: Property?, tvPropertyName: TextView, tvPropertyAddress: TextView, tvPropertyDescription: TextView) {
        tvPropertyName.text = property?.name
        tvPropertyName.textSize = 20f
        tvPropertyName.setTypeface(null, Typeface.BOLD)

        tvPropertyAddress.text = property?.address
        tvPropertyAddress.textSize = 20f
        tvPropertyAddress.setTypeface(null, Typeface.BOLD)

        tvPropertyDescription.text = property?.description
        tvPropertyDescription.textSize = 20f
        tvPropertyDescription.setTypeface(null, Typeface.BOLD)
    }
}

