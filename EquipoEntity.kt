package pmdm.laligaapi.core.network


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EquiposClient {

    @GET("/equipo/equipos")
    suspend fun getEquipos(): Response<List<EquipoResponse>>

    @GET("/equipo/equipos/{equipoId}")
    suspend fun getEquipo(@Path("equipoId") idPost: Long): Response<EquipoResponse>


    @POST("/equipo/equipos/{equipoId}/jugadores")
    suspend fun addJugador(
        @Path("equipoId") idPost: Long,
        @Body comentario: JugadorResponse
    ): Response<JugadorResponse>


    @DELETE("/equipo/equipos/{equipoId}")
    suspend fun removeEquipo(@Path("equipoId") idEquipo: Long): Response<Unit>

    @POST("/equipo/equipos")
    suspend fun addEquipos(@Body post: EquipoResponse): Response<EquipoResponse>
}