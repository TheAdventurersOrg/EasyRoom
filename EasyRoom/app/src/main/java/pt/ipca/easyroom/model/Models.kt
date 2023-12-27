package pt.ipca.easyroom.model

interface User {
    val firstName: String
    val lastName: String
    val email: String
    val phoneNumber: String
    val userType: String
}

data class Owner(
    override val firstName: String,
    override val lastName: String,
    override val email: String,
    override val phoneNumber: String
) : User {
    override val userType: String
        get() = "Owner"
}

data class Tenant(
    override val firstName: String,
    override val lastName: String,
    override val email: String,
    override val phoneNumber: String
) : User {
    override val userType: String
        get() = "Tenant"
}

data class Property(
    val name: String,
    val address: String,
    val description: String,
    val ownerId: String
)



