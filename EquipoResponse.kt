package pmdm.laligaapi.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pmdm.laligaapi.equipos.data.AppDatabase
import pmdm.laligaapi.equipos.data.EquipoDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "equiposdb"
        ).build()
    }

    @Provides
    fun provideEquipoDao(database: AppDatabase): EquipoDao {
       return database.equipoDao()
    }
}