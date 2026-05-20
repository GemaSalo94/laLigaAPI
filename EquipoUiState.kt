package pmdm.laligaapi.equipos.domain

import pmdm.laligaapi.equipos.data.EquipoRepository
import javax.inject.Inject

class SyncEquiposUseCase @Inject constructor(private val postRepository: EquipoRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return EquipoRepository.syncEquiposToLocal()
    }
}