package pmdm.laligaapi.core.network


import com.google.gson.annotations.SerializedName

data class EquipoResponse(

    @SerializedName("id")
    val id: Long?,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("fecha")
    val fechaFundacion: String,

    @SerializedName("socios")
    val numeroSocios: Int,

    @SerializedName("jugadores")
    val jugadores: List<JugadorResponse>,

    @SerializedName("escudo")
    val escudo: String
)