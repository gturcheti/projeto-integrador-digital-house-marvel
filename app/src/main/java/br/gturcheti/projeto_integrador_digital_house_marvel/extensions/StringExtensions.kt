package br.gturcheti.projeto_integrador_digital_house_marvel.extensions

fun String.toHttps(): String {
    val sb = StringBuilder(this)
    sb.deleteRange(0, 4)
    sb.insertRange(0, "https", 0, 5)
    return sb.toString()
}