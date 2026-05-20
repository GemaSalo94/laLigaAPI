package pmdm.laligaapi.core.network

import com.google.gson.annotations.SerializedName
data class JugadorResponse(

    @SerializedName("id")
    val id: Long?,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("posicion")
    val posicion: String,
    @SerializedName("dorsal")
    val dorsal: Int


)