package pmdm.laligaapi.equipos.ui.model

import androidx.annotation.DrawableRes
import pmdm.laligaapi.R

data class Equipo(
    val id: Long,
    val nombre: String,
    val fechaFundacion: String,
    val numeroSocios: Int,
    val escudo: String,
    val jugadores: List<Jugador>
){
    fun generarIcono(): Int{
        return when(escudo){
            "real_madrid.png" -> R.drawable.real_madrid
            "barcelona.png" -> R.drawable.barcelona
            "atletico_madrid.png" -> R.drawable.atletico_madrid
            "real_sociedad.png" -> R.drawable.real_sociedad
            "villareal.png" -> R.drawable.villareal
            "betis.png" -> R.drawable.betis
            "athletic_bilbao.png" -> R.drawable.athletic_bilbao
            "rayo_vallecano.png" -> R.drawable.rayo_vallecano
            else -> R.drawable.real_madrid
        }
    }
}

