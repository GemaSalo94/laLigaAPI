package pmdm.laligaapi.equipos.data

import com.google.gson.Gson
import pmdm.laligaapi.core.network.EquipoResponse
import pmdm.laligaapi.core.network.EquiposClient
import pmdm.laligaapi.core.network.JugadorResponse
import pmdm.laligaapi.equipos.mappers.toJugadorResponse
import pmdm.laligaapi.equipos.mappers.toModel
import pmdm.laligaapi.equipos.ui.model.Jugador
import retrofit2.Response
import javax.inject.Inject
import kotlin.collections.emptyList

    class EquipoRepository @Inject constructor(
        private val equiposClient: EquiposClient,
        private val equipoDao: EquipoDao){

        suspend fun getEquipos(): Response<List<EquipoResponse>>{
            return equiposClient.getEquipos()
        }

        suspend fun getEquipo(idEquipo: Long): Response<EquipoResponse>{
            return equiposClient.getEquipo(idEquipo)
        }
        suspend fun addJugador(idEquipo: Long, jugador: Jugador) : Response<JugadorResponse>{
            return equiposClient.addJugador(idEquipo, jugador.toJugadorResponse())
        }

        suspend fun removeEquipo(idEquipo: Long): Response<Unit>{
            return equiposClient.removeEquipo(idEquipo)
        }

        suspend fun syncEquiposToLocal(): Result<Unit> {
            return try {
                val response = equiposClient.getEquipos()
                if (response.isSuccessful) {
                    val networkEquipos = response.body() ?: emptyList()
                    val gson = Gson()

                    // Mapeamos los datos de red a Entidades de Room
                    val entities = networkEquipos.map { equipoNet ->
                        EquipoEntity(
                            id = equipoNet.id ?: 0,
                            nombre = equipoNet.nombre,
                            fechaFundacion = equipoNet.fechaFundacion,
                            numeroSocios = equipoNet.numeroSocios,
                            escudo = equipoNet.escudo,
                            jugadores = gson.toJson(equipoNet.jugadores.map { it.toModel() })
                        )
                    }
                    // Limpiamos y guardamos los nuevos
                    equipoDao.clearAll()
                    equipoDao.insertAll(entities)

                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Error de red: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        companion object {
            fun syncEquiposToLocal(): Result<Unit> {

                return Result.success(Unit)
            }
        }
    }
