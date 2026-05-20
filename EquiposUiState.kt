package pmdm.laligaapi.equipos.domain

import pmdm.laligaapi.equipos.data.EquipoRepository
import javax.inject.Inject


class RemoveEquipoUseCase @Inject constructor(private val equipoRepository: EquipoRepository) {
    suspend operator fun invoke(idEquipo: Long): Result<Unit>{
        return try {
            // Hacemos la llamada asíncrona al repositorio
            val response = equipoRepository.removeEquipo(idEquipo)

            // Comprobamos si la respuesta HTTP es 200-299 (OK)
            if(response.isSuccessful)
                Result.success(Unit) // Éxito, no devolvemos nada (Unit)
            else
            // Si la API devuelve un error (ej: 404, 500), lo capturamos
                Result.failure(Exception("Error en la petición: ${response.code()}"))

        } catch(e: Exception) {
            // Si hay un error de conexión (no hay internet, timeout...), entra por este catch
            Result.failure(e)
        }
    }
}