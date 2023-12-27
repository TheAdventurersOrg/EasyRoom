package pt.ipca.easyroom.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.easyroom.R
import pt.ipca.easyroom.model.Property
import pt.ipca.easyroom.data.DataSourceActivity

class AddPropertyActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dataSourceActivity: DataSourceActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        val btToCreate = findViewById<Button>(R.id.btToCreate)

        auth = FirebaseAuth.getInstance()
        dataSourceActivity = DataSourceActivity(this)

        btToCreate.setOnClickListener {
            val propertyName = findViewById<EditText>(R.id.etPropertyName).text.toString()
            val propertyAddress = findViewById<EditText>(R.id.etPropertyAddress).text.toString()
            val propertyDescription = findViewById<EditText>(R.id.etPropertyDescription).text.toString()

            if (propertyName.isBlank() || propertyAddress.isBlank() || propertyDescription.isBlank()) {
                Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
            } else {
                val user = auth.currentUser
                if (user != null) {
                    val newPropertyId = dataSourceActivity.generateNewPropertyId()
                    val newProperty = Property(newPropertyId, propertyName, propertyAddress, propertyDescription, user.uid)
                    dataSourceActivity.addProperty(newProperty)
                    Toast.makeText(this, "Property added successfully.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, PropertiesActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "No current user", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

