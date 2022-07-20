package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import android.content.Context
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
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toHttps
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroVO
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HeroListViewModel : ViewModel() {

    val IMG_NOT_AVAIBLE = "image_not_available"

    private val charRepository: MarvelApiRepository = MarvelApiRepository()
    private val userRepository: UserRepository = UserRepository()

    private val _heroItem: MutableLiveData<Result<List<HeroVO>>> = MutableLiveData()
    val heroItem: LiveData<Result<List<HeroVO>>> = _heroItem


    fun fetchHeroListOnQueryTextChange(queryName: String?) {
        _heroItem.value = Result.Loading
        viewModelScope.launch {
            queryName?.let { query ->
                try {
                    charRepository.fetchCharactersListByNameStartsWith(query).let { response ->
                        val vo = mapCharacters(response.data.results)
                        _heroItem.value = Result.Success(vo)
                    }
                } catch (ex: Exception) {
                    _heroItem.value = Result.Error
                }
            }
        }
    }

    fun fetchHeroListOnQueryTextSubmit(queryName: String?) {
        _heroItem.value = Result.Loading
        viewModelScope.launch {
            queryName?.let { query ->
                try {
                    charRepository.fetchCharactersListbyName(query).let { response ->
                        val vo = mapCharacters(response.data.results)
                        _heroItem.value = Result.Success(vo)
                    }
                } catch (ex: Exception) {
                    _heroItem.value = Result.Error
                }
            }
        }
    }

    fun fetchLocalFavorites(context: Context) {
        _heroItem.value = Result.Loading
        viewModelScope.launch() {
            getCurrentUserEmail().let { email ->
                viewModelScope.launch {
                    getUserDao(context).getUserWithCharacters(email).collect { crossList ->
                        crossList?.forEach {
                            it.characters.let { list ->
                                val vo = list.mapLocalCharacters()
                                _heroItem.value = Result.Success(vo)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun mapCharacters(list: List<CharacterDTO>): List<HeroVO> {
        return list.filterCharacters()
            .map { heroiDTO ->
                HeroVO(
                    id = heroiDTO.id.toString(),
                    name = heroiDTO.name,
                    description = heroiDTO.description,
                    image = "${heroiDTO.image.path.toHttps()}" + "/standard_fantastic" + ".${heroiDTO.image.extension}",
                    url = "${heroiDTO.urls.firstOrNull()?.url}"
                )
            }
    }

    private fun List<Character>.mapLocalCharacters(): List<HeroVO> =
        this.map { character ->
            HeroVO(
                id = character.characterId,
                name = character.name,
                description = character.description,
                image = character.image,
                url = character.url
            )
        }

    private fun List<CharacterDTO>.filterCharacters(): List<CharacterDTO> =
        this.filter { character ->
            !character.image.path.contains(IMG_NOT_AVAIBLE)
                    && character.image.extension.isNotEmpty()
                    && character.name.isNotEmpty()
                    && character.description.isNotEmpty()
                    && character.urls.isNotEmpty()
        }


    private fun getCurrentUserEmail(): String {
        return userRepository.currentUser.value?.email.toString()
    }

    private fun getUserDao(context: Context): UserDAO {
        return AppDatabase.instancia(context).userDAO()
    }


}