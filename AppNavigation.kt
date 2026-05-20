package pmdm.laligaapi.equipos.mappers
import pmdm.laligaapi.core.network.EquipoResponse
import pmdm.laligaapi.core.network.JugadorResponse
import pmdm.laligaapi.equipos.ui.model.Equipo
import pmdm.laligaapi.equipos.ui.model.Jugador
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun EquipoResponse.toModel(): Equipo {
    return Equipo(
        id = id ?: 0L,
        nombre = nombre,
        fechaFundacion = fechaFundacion,
        numeroSocios = numeroSocios,
        escudo = escudo,
        checked = false,
        jugadores = jugadores.map { it.toModel() }
    )
}

fun JugadorResponse.toModel(): Jugador{
    return Jugador(
        id = id,
        nombre = nombre,
        posicion = posicion,
        dorsal = dorsal,
        checked = false
    )
}

fun Equipo.toEquipoResponse(): EquipoResponse  {
    return EquipoResponse(
        id = id,
        nombre = nombre,
        fechaFundacion = fechaFundacion,
        numeroSocios = numeroSocios,
        escudo = escudo,
        jugadores = jugadores.map { it.toJugadorResponse() }
    )
}

fun Equipo.formatDate(fecha: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val formatterES = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val date = LocalDate.parse(fecha, formatterES)

    return date.format(formatter)
}

fun Jugador.toJugadorResponse(): JugadorResponse {
    return JugadorResponse(
        id = id,
        nombre = nombre,
        posicion = posicion,
        dorsal = dorsal,
    )
}