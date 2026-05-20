package pmdm.laligaapi.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pmdm.laligaapi.core.network.EquiposClient
import pmdm.laligaapi.utils.Constantes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Construye el objeto Retrofit con esta configuración.
    }

    @Provides
    @Singleton
    fun provideEquiposClient(retrofit: Retrofit): EquiposClient {
        return retrofit.create(EquiposClient::class.java)
    }
}
