package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.MarvelApiRepository
import br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.dto.CharacterDTO
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toHttps
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HeroiListaViewModel : ViewModel() {

    val IMG_NOT_AVAIBLE = "image_not_available"

    private val repository: MarvelApiRepository = MarvelApiRepository()

    private val _heroItem: MutableLiveData<Result<List<HeroiVO>>> = MutableLiveData()
    val heroItem: LiveData<Result<List<HeroiVO>>> = _heroItem


    fun fetchHeroList() {
        _heroItem.value = Result.Loading
        viewModelScope.launch {
            try {
                repository.fetchCharactersList().let { response ->
                    val vo = mapCharacters(response.data.results)
                    _heroItem.value = Result.Success(vo)
                }
            } catch (ex: HttpException) {
                _heroItem.value = Result.Error
            }
        }
    }

    fun fetchHeroListOnQueryTextChange(queryName: String?) {
        _heroItem.value = Result.Loading
        viewModelScope.launch {
            queryName?.let { query ->
                try {
                    repository.fetchCharactersListByNameStartsWith(query).let { response ->
                        val vo = mapCharacters(response.data.results)
                        _heroItem.value = Result.Success(vo)
                    }
                } catch (ex: HttpException) {
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
                    repository.fetchCharactersListbyName(query).let { response ->
                        val vo = mapCharacters(response.data.results)
                        _heroItem.value = Result.Success(vo)
                    }
                } catch (ex: HttpException) {
                    _heroItem.value = Result.Error
                }
            }
        }
    }

    private fun mapCharacters(list: List<CharacterDTO>): List<HeroiVO> {
        return list.filter { item -> filterCharacters(item = item) }
            .map { heroiDTO ->
                HeroiVO(
                    id = heroiDTO.id.toString(),
                    name = heroiDTO.name,
                    description = heroiDTO.description,
                    image = "${heroiDTO.image.path.toHttps()}" + "/standard_fantastic" + ".${heroiDTO.image.extension}"
                )
            }
    }

    private fun filterCharacters(item: CharacterDTO): Boolean {
        return !item.image.path.contains(IMG_NOT_AVAIBLE)
                && item.image.extension.isNotEmpty()
                && item.name.isNotEmpty()
                && item.description.isNotEmpty()

    }

}