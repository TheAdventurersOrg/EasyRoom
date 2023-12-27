package pt.ipca.easyroom.screen

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.easyroom.R
import pt.ipca.easyroom.data.DataSourceActivity
import pt.ipca.easyroom.model.Property

class UpdatePropertyActivity : AppCompatActivity() {
    private lateinit var propertyId: String
    private lateinit var dataSource: DataSourceActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_property)

        propertyId = intent.getStringExtra("PROPERTY_ID") ?: ""
        dataSource = DataSourceActivity(this)

        dataSource.getPropertyById(propertyId) { property ->
            findViewById<EditText>(R.id.etPropertyName).setText(property?.name)
            findViewById<EditText>(R.id.etPropertyAddress).setText(property?.address)
            findViewById<EditText>(R.id.etPropertyDescription).setText(property?.description)
        }

        findViewById<Button>(R.id.btSaveChanges).setOnClickListener {
            val propertyName = findViewById<EditText>(R.id.etPropertyName).text.toString()
            val propertyAddress = findViewById<EditText>(R.id.etPropertyAddress).text.toString()
            val propertyDescription = findViewById<EditText>(R.id.etPropertyDescription).text.toString()

            if (propertyName.isBlank() || propertyAddress.isBlank() || propertyDescription.isBlank()) {
                Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
            } else {
                val updatedProperty = Property(
                    id = propertyId,
                    name = propertyName,
                    address = propertyAddress,
                    description = propertyDescription,
                    ownerId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                )
                dataSource.updateProperty(propertyId, updatedProperty)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Property updated successfully.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, PropertiesActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to update property.", Toast.LENGTH_SHORT).show()
                        Log.w(TAG, "Error updating property.", e)
                    }
            }
        }
    }
}
