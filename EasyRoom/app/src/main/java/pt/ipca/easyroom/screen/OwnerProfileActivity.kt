package pt.ipca.easyroom.screen

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.easyroom.R
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Owner

class OwnerProfileActivity : AppCompatActivity() {
    private lateinit var tenantId: String
    private lateinit var dataSource: DataSourceActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_profile)

        tenantId = intent.getStringExtra("TENANT_ID") ?: ""
        dataSource = DataSourceActivity(this)

        val tvOwnerName = findViewById<TextView>(R.id.tvOwnerName)
        val tvOwnerPhone = findViewById<TextView>(R.id.tvOwnerPhone)
        val tvOwnerEmail = findViewById<TextView>(R.id.tvOwnerEmail)

        dataSource.getOwnerOfTenant(tenantId) { owner ->
            displayOwnerInfo(owner, tvOwnerName, tvOwnerPhone, tvOwnerEmail)
        }
    }

    private fun displayOwnerInfo(owner: Owner?, tvOwnerName: TextView, tvOwnerPhone: TextView, tvOwnerEmail: TextView) {
        tvOwnerName.text = getString(R.string.owner_name_concatenation, owner?.firstName, owner?.lastName)
        tvOwnerName.textSize = 20f
        tvOwnerName.setTypeface(null, Typeface.BOLD)

        tvOwnerPhone.text = owner?.phoneNumber
        tvOwnerPhone.textSize = 20f
        tvOwnerPhone.setTypeface(null, Typeface.BOLD)

        tvOwnerEmail.text = owner?.email
        tvOwnerEmail.textSize = 20f
        tvOwnerEmail.setTypeface(null, Typeface.BOLD)
    }
}

