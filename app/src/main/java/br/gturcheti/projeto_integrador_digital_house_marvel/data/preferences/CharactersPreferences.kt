package br.gturcheti.projeto_integrador_digital_house_marvel.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.characterDataStore: DataStore<Preferences> by preferencesDataStore(name = "characterId")

val characterIdPreference = stringPreferencesKey("characterId")