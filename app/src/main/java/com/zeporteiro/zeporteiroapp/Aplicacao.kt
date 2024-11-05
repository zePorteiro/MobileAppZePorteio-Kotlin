package com.zeporteiro.zeporteiroapp

import android.app.Application
import com.zeporteiro.zeporteiroapp.di.moduloUsuario
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class Aplicacao: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@Aplicacao)

            // aqui estamos dizendo para o Koin usar o módulo que criamos
            modules(moduloUsuario)
            // modules(moduloXYZ)
            // poderiamos ter mais de um módulo, se necessário
        }
    }
}