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
import pt.ipca.easyroom.model.Room
import pt.ipca.easyroom.screen.PropertyActivity
import pt.ipca.easyroom.screen.UpdateRoomActivity
import pt.ipca.easyroom.screen.RoomActivity
import pt.ipca.easyroom.screen.RoomInformationActivity

class RoomAdapter(private val rooms: List<Room>, private val dataSource: DataSourceActivity, private val context: Context, private val propertyId: String) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_room, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = rooms[position]

        val tvRoomName = holder.view.findViewById<TextView>(R.id.tvRoomName)
        tvRoomName.text = room.name

        tvRoomName.setOnClickListener {
            val intent = Intent(context, RoomActivity::class.java)
            intent.putExtra("ROOM_NAME", room.name)
            context.startActivity(intent)
        }

        val ivRoomInformation = holder.view.findViewById<ImageView>(R.id.ivRoomInformation)
        ivRoomInformation.setOnClickListener {
            val intent = Intent(context, RoomInformationActivity::class.java)
            intent.putExtra("ROOM_ID", room.id)
            context.startActivity(intent)
        }

        val ivEditRoom = holder.view.findViewById<ImageView>(R.id.ivEditRoom)
        ivEditRoom.setOnClickListener {
            val intent = Intent(context, UpdateRoomActivity::class.java)
            intent.putExtra("ROOM_ID", room.id)
            intent.putExtra("PROPERTY_ID", propertyId)
            context.startActivity(intent)
        }

        val ivDeleteRoom = holder.view.findViewById<ImageView>(R.id.ivDeleteRoom)
        ivDeleteRoom.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete Room")
                .setMessage("Are you sure you want to delete this room?")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    dataSource.deleteRoom(room.id)
                    val intent = Intent(context, PropertyActivity::class.java)
                    intent.putExtra("PROPERTY_ID", propertyId)
                    context.startActivity(intent)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(R.drawable.ic_dialog_alert)
                .show()
        }
    }

    override fun getItemCount() = rooms.size
}
