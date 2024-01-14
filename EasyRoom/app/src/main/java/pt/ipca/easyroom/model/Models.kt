package pt.ipca.easyroom.model

interface User {
    val firstName: String
    val lastName: String
    val email: String
    val phoneNumber: String
    val userType: String
}

data class Owner(
    val id: String = "",
    override val firstName: String = "",
    override val lastName: String = "",
    override val email: String = "",
    override val phoneNumber: String = "",
    override var userType: String = "Owner"
) : User

data class Tenant(
    val id: String = "",
    override val firstName: String = "",
    override val lastName: String = "",
    override val email: String = "",
    override val phoneNumber: String = "",
    var roomId: String = "none",
    override var userType: String = "Tenant"
) : User

data class Property(
    var id: String = "",
    val name: String = "",
    val address: String = "",
    val description: String = "",
    val ownerId: String = ""
)

data class Room(
    var id: String = "",
    val name: String = "",
    val description: String = "",
    val propertyId: String = ""
)

data class Message(
    val text: String = "",
    val senderName: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val timestamp: Long = System.currentTimeMillis()
)



