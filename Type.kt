package pmdm.laligaapi.equipos.ui.model

import pmdm.laligaapi.R


data class Jugador(
    val id: Long?,
    val nombre: String,
    val posicion: String,
    val dorsal: Int
){
    fun generarIcono(): Int{
        return when(posicion){
            "Portero" -> R.drawable.portero
            "Defensa" -> R.drawable.defensa
            "Centrocampista" -> R.drawable.centrocampista
            "Delantero" -> R.drawable.delantero
            else -> R.drawable.delantero
        }
    }
}
