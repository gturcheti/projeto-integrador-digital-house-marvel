package br.gturcheti.projeto_integrador_digital_house_marvel.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "sessao_usuario")

val usuarioLogadoPreferences = stringPreferencesKey("usuarioLogado")