package pmdm.laligaapi.equipos.domain

import pmdm.laligaapi.equipos.data.EquipoRepository
import pmdm.laligaapi.equipos.mappers.toModel
import pmdm.laligaapi.equipos.ui.model.Equipo
import javax.inject.Inject


class GetEquiposUseCase @Inject constructor(private val equipoRepository: EquipoRepository) {
    suspend operator fun invoke(): Result<List<Equipo>> {
        return try {
            val response = equipoRepository.getEquipos()

            if (response.isSuccessful)
                Result.success(response.body()!!.map { it.toModel() })
            else
                Result.failure(Exception("Error en la petición: ${response.code()}"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}