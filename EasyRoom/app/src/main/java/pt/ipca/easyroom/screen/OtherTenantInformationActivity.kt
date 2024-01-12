package pt.ipca.easyroom.screen

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.easyroom.R
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Tenant

class OtherTenantInformationActivity : AppCompatActivity() {
    private lateinit var tenantId: String
    private lateinit var dataSource: DataSourceActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_tenant_information)

        tenantId = intent.getStringExtra("TENANT_ID") ?: ""
        dataSource = DataSourceActivity(this)

        val tvTenantName = findViewById<TextView>(R.id.tvTenantNameInfo)
        val tvTenantPhone = findViewById<TextView>(R.id.tvTenantPhone)
        val tvTenantEmail = findViewById<TextView>(R.id.tvTenantEmail)

        dataSource.getTenantById(tenantId) { tenant ->
            displayTenantInfo(tenant, tvTenantName, tvTenantPhone, tvTenantEmail)
        }
    }

    private fun displayTenantInfo(tenant: Tenant?, tvTenantName: TextView, tvTenantPhone: TextView, tvTenantEmail: TextView) {
        tvTenantName.text = getString(R.string.tenant_name_concatenation, tenant?.firstName, tenant?.lastName)
        tvTenantName.textSize = 20f
        tvTenantName.setTypeface(null, Typeface.BOLD)

        tvTenantPhone.text = tenant?.phoneNumber
        tvTenantPhone.textSize = 20f
        tvTenantPhone.setTypeface(null, Typeface.BOLD)

        tvTenantEmail.text = tenant?.email
        tvTenantEmail.textSize = 20f
        tvTenantEmail.setTypeface(null, Typeface.BOLD)
    }
}
