package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gturcheti.projeto_integrador_digital_house_marvel.database.api_marvel.MarvelApiRepository
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toHttps
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HeroiListaViewModel : ViewModel() {

    private val repository: MarvelApiRepository = MarvelApiRepository()

    private val _heroItem: MutableLiveData<Result<List<HeroiVO>>> = MutableLiveData()
    val heroItem: LiveData<Result<List<HeroiVO>>> = _heroItem

    fun fetchHerois() {
        _heroItem.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = repository.fetchCharactersList("100", "0")
                val vo = response.data.results.map { heroiDTO ->
                    HeroiVO(
                        id = heroiDTO.id.toString(),
                        name = heroiDTO.name,
                        description = heroiDTO.description,
                        image = "${heroiDTO.image.path.toHttps()}/" + "standard_fantastic" + ".${heroiDTO.image.extension}"
                    )
                }
                _heroItem.value = Result.Success(vo)
            } catch (ex: HttpException) {
                _heroItem.value = Result.Error
            }
        }
    }


}