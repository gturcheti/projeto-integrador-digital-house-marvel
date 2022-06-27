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

    val URL = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"

    private val repository: MarvelApiRepository = MarvelApiRepository()
    private val _heroItem: MutableLiveData<Result<List<HeroiVO>>> = MutableLiveData()
    val heroItem: LiveData<Result<List<HeroiVO>>> = _heroItem

    fun fetchHerois() {
        _heroItem.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = repository.fetchCharactersList("0")
                val filter = response.data.results.filter { item -> filterCharacters(item = item) }
                val vo = filter.map { heroiDTO ->
                    HeroiVO(
                        id = heroiDTO.id.toString(),
                        name = heroiDTO.name,
                        description = heroiDTO.description,
                        image = "${heroiDTO.image.path.toHttps()}" + "/standard_fantastic" + ".${heroiDTO.image.extension}"
                    )
                }
                _heroItem.value = Result.Success(vo)
            } catch (ex: HttpException) {
                _heroItem.value = Result.Error
            }
        }
    }

    private fun filterCharacters(item: CharacterDTO): Boolean {
        return item.image.path != URL
                && item.image.extension.isNotEmpty()
                && item.name.isNotEmpty()
                && item.description.isNotEmpty()

    }



}