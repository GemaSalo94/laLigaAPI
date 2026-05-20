package pmdm.laligaapi.equipos.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
    @Dao
    interface EquipoDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAll(equipos: List<EquipoEntity>)

        @Query("DELETE FROM equipo")
        suspend fun clearAll()
    }
