package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gturcheti.projeto_integrador_digital_house_marvel.data.AppDatabase
import br.gturcheti.projeto_integrador_digital_house_marvel.data.MarvelApiRepository
import br.gturcheti.projeto_integrador_digital_house_marvel.data.UserRepository
import br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.characters.CharacterDTO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.dao.CharacterDAO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.dao.UserDAO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.Character
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.relations.UserCharacterCrossRef
import br.gturcheti.projeto_integrador_digital_house_marvel.data.preferences.characterDataStore
import br.gturcheti.projeto_integrador_digital_house_marvel.data.preferences.characterIdPreference
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toHttps
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroVO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HeroViewModel : ViewModel() {

    private val charRepository: MarvelApiRepository = MarvelApiRepository()
    private val userRepository: UserRepository = UserRepository()

    private val _character: MutableLiveData<Result<HeroVO>> = MutableLiveData()
    val character: LiveData<Result<HeroVO>> = _character

    private val _favorite: MutableLiveData<Boolean> = MutableLiveData()
    val favorite: LiveData<Boolean> = _favorite

    private lateinit var characterID: String

    fun fetchCharacterById(context: Context) {
        _character.value = Result.Loading

        collectCharacterId(context)

        viewModelScope.launch {
            lateinit var vo: HeroVO
            getCharacterDao(context).findLocalCharacterById(characterID).collect {
                it?.let { localCharacter ->
                    vo = daoToVO(localCharacter)
                    _favorite.value = true
                    _character.value = Result.Success(vo)
                } ?: viewModelScope.launch {
                    try {
                        val response = charRepository.fetchCharactersById(characterID)
                        response.data.results.firstOrNull()?.let { characterDTO ->
                            vo = dtoToVO(characterDTO)
                        }
                        _favorite.value = false
                        _character.value = Result.Success(vo)
                    } catch (ex: HttpException) {
                        _character.value = Result.Error
                    }
                }
            }
        }
    }

    private fun collectCharacterId(contex: Context) {
        viewModelScope.launch {
            contex.characterDataStore.data.collect { preferences ->
                preferences[characterIdPreference]?.let { characterId ->
                    characterID = characterId
                    Log.i("PREFERENCES", "collectCharacterId: $characterId")
                }
            }
        }
    }

    fun saveFavoriteHero(context: Context, hero: HeroVO) {
        viewModelScope.launch {
            userRepository.currentUser.value?.email.let { email ->
                val relation = UserCharacterCrossRef(email.toString(), hero.id)
                getUserDao(context).associateCharacterByUser(relation)
                getCharacterDao(context).saveLocalHero(voToDAO(hero))
            }
            Log.i("HERO_VIEW_MODEL", "savedFavoriteHero: $hero.id")
        }
    }

    fun removeFavoriteHero(context: Context, hero: HeroVO) {
        viewModelScope.launch {
            userRepository.currentUser.value?.email.let { email ->
                val crossRef = UserCharacterCrossRef(email.toString(), hero.id)
                getUserDao(context).deleteRelationCharacterByUser(crossRef)
                getCharacterDao(context).deleteCharacter(voToDAO(hero))
            }
            Log.i("HERO_VIEW_MODEL", "removedFavoriteHero: $hero.id")
        }
    }

    private fun getCharacterDao(context: Context): CharacterDAO {
        return AppDatabase.instancia(context).characterDAO()
    }

    private fun getUserDao(context: Context): UserDAO {
        return AppDatabase.instancia(context).userDAO()
    }

    private fun dtoToVO(dto: CharacterDTO): HeroVO {
        return HeroVO(
            id = dto.id.toString(),
            name = dto.name,
            description = dto.description,
            image = "${dto.image.path.toHttps()}/" + "standard_fantastic" + ".${dto.image.extension}",
            url = "${getUrlCharacter(dto)}"
        )
    }

    private fun daoToVO(dao: Character): HeroVO {
        return HeroVO(dao.characterId, dao.name, dao.description, dao.image, dao.url)
    }

    private fun voToDAO(vo: HeroVO): Character {
        return Character(vo.id, vo.name, vo.description, vo.image, vo.url)
    }

    private fun getUrlCharacter(dto: CharacterDTO): String {
        var uri = "https://marvel.com"
        dto.urls.firstOrNull()?.let {
            uri = it.url.toHttps()
            Log.i("HERO_VIEW_MODEL", "getUrlCharacter: $uri")
        }
        return uri
    }

    fun shareHeroOnSocialMedia(context: Context, message: String) {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, message)
        context.startActivity(intent)
    }

    fun seeMoreOnMarvelSiteIntent(context: Context, uri: String) {
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

}