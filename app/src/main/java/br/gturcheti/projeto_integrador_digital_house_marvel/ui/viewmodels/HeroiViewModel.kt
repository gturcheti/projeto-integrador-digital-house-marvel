package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.MarvelApiRepository
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toHttps
import br.gturcheti.projeto_integrador_digital_house_marvel.preferences.characterDataStore
import br.gturcheti.projeto_integrador_digital_house_marvel.preferences.characterIdPreference
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HeroiViewModel : ViewModel() {

    private val repository: MarvelApiRepository = MarvelApiRepository()

    private val _character: MutableLiveData<Result<HeroiVO>> = MutableLiveData()
    val character: LiveData<Result<HeroiVO>> = _character

    private lateinit var characterID:String

    fun fetchCharacterById(contex: Context) {
        _character.value = Result.Loading

        collectCharacterId(contex)

        viewModelScope.launch {
            try {
                val response = repository.fetchCharactersById(characterID)
                lateinit var vo: HeroiVO
                response.data.results.firstOrNull()?.let { characterDTO ->
                    vo = HeroiVO(
                        id = characterDTO.id.toString(),
                        name = characterDTO.name,
                        description = characterDTO.description,
                        image = "${characterDTO.image.path.toHttps()}/" + "standard_fantastic" + ".${characterDTO.image.extension}"
                    )
                }
                _character.value = Result.Success(vo)

            } catch (ex: HttpException) {
                _character.value = Result.Error
            }
        }
    }

     fun  collectCharacterId(contex : Context) {
        viewModelScope.launch {
            contex.characterDataStore.data.collect{ preferences ->
                preferences[characterIdPreference]?.let { characterId ->
                    characterID = characterId
                    Log.i("PREFERENCES", "collectCharacterId: $characterId")
                }
            }
        }
    }
}