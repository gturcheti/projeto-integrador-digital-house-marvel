package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    object Loading : Result<Nothing>()
    object Error : Result<Nothing>()
}