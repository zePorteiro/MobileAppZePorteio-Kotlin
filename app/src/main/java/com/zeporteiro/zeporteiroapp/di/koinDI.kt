package com.zeporteiro.zeporteiroapp.di

import com.zeporteiro.zeporteiroapp.data.datastore.DataStoreManager
import com.zeporteiro.zeporteiroapp.network.ApiZePorteiro
import com.zeporteiro.zeporteiroapp.network.RetroFitService
import com.zeporteiro.zeporteiroapp.repository.AuthRepository
import com.zeporteiro.zeporteiroapp.viewModel.ListaEncomendaViewModel
import com.zeporteiro.zeporteiroapp.viewModel.LoginPageViewModel
import com.zeporteiro.zeporteiroapp.viewModel.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val moduloUsuario = module {
    single {
        DataStoreManager(get())
    }

    // Singleton para SessaoUsuario
    single {
        SessaoUsuario(get())
    }

    // Singleton para ApiZePorteiro
    single<ApiZePorteiro> {
        RetroFitService.getApi(get<SessaoUsuario>().token)
    }

    // Singleton para AuthRepository
    single {
        AuthRepository(
            apiZePorteiro = get(),
            sessaoUsuario = get(),
            dataStoreManager = get()
        )
    }

    // ViewModel
    viewModel {
        LoginPageViewModel(
            authRepository = get(),
            dataStoreManager = get(),
            apiZe = get()
        )
    }

    single {
        SignUpViewModel(
            api = get()
        )
    }

    viewModel { ListaEncomendaViewModel(get(), get()) }
}