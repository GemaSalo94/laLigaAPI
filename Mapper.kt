package pmdm.laligaapi.equipos.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pmdm.laligaapi.equipos.ui.model.Jugador
@Entity(tableName = "equipo")
class EquipoEntity (
    @PrimaryKey val id: Long,
    val nombre: String,
    val fechaFundacion: String,
    val numeroSocios: Int,
    val escudo: String,
    val jugadores: String?
)
    class Converters {
        private val gson = Gson()

        @TypeConverter
        fun fromJugadoresList(jugadores: List<Jugador>?): String {
            return gson.toJson(jugadores)
        }

        @TypeConverter
        fun toJugadoresList(jugadoresString: String?): List<Jugador> {
            if (jugadoresString == null) return emptyList()
            val type = object : TypeToken<List<Jugador>>() {}.type
            return gson.fromJson(jugadoresString, type)
        }
    }