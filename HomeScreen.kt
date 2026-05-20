package pmdm.laligaapi.equipos.domain

import pmdm.laligaapi.equipos.data.EquipoRepository
import pmdm.laligaapi.equipos.mappers.toModel
import pmdm.laligaapi.equipos.ui.model.Equipo
import javax.inject.Inject

class GetEquipoUseCase @Inject constructor(private val equipoRepository: EquipoRepository) {

    suspend operator fun invoke(idEquipo: Long): Result<Equipo>{
        return try {
            val response = equipoRepository.getEquipo(idEquipo)

            if(response.isSuccessful)
            // Extraemos el cuerpo de la respuesta de red y lo convertimos a modelo de UI
                Result.success(response.body()!!.toModel() )
            else
                Result.failure(Exception("Error en la petición: ${response.code()}"))

        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}