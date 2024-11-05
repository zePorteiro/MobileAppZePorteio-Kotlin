package com.zeporteiro.zeporteiroapp.di

import com.zeporteiro.zeporteiroapp.network.ApiZePorteiro
import com.zeporteiro.zeporteiroapp.network.RetroFitService
import com.zeporteiro.zeporteiroapp.repository.AuthRepository
import com.zeporteiro.zeporteiroapp.viewModel.LoginPageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val moduloUsuario = module {
    // Singleton para SessaoUsuario
    single { SessaoUsuario() }

    // Singleton para ApiZePorteiro
    single {
        RetroFitService.getApi(get<SessaoUsuario>().token)
    }

    // Singleton para AuthRepository
    single {
        AuthRepository(
            apiZePorteiro = get(),
            sessaoUsuario = get()
        )
    }

    // ViewModel
    viewModel {
        LoginPageViewModel(
            authRepository = get()
        )
    }

    /*
    single -> indica que o objeto será instanciado uma única vez
              Ou seja, todos os lugares que pedirem um objeto do tipo SessaoUsuario
              receberão a MESMA instância
     */
//    single<SessaoUsuario> {
//        // estamos dizendo para o Koin como criar um objeto do tipo SessaoUsuario para o primeiro que pedir
//        // da 2a vez em diante, usará a instância criada anteriormente
//        SessaoUsuario()
//    }

//    single<ApiZePorteiro> {
//        // estamos dizendo para o Koin como criar um objeto do tipo ApiFilmes para o primeiro que pedir
//        // o get<SessaoUsuario>() é uma forma de pedir ao Koin para entregar um objeto do tipo SessaoUsuario
//        RetroFitService.getApi(get<SessaoUsuario>().token)
//    }

//    single { AuthRepository(get(), get()) }
//    single { LoginPageViewModel(get()) }


//    single { AuthRepository(get(), get()) }

}