package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gturcheti.projeto_integrador_digital_house_marvel.data.MarvelApiRepository
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.HeroiVO
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HeroiListaViewModel : ViewModel() {

    private val repository: MarvelApiRepository = MarvelApiRepository()

    private val _herois: MutableLiveData<Result<List<HeroiVO>>> = MutableLiveData()
    val herois: LiveData<Result<List<HeroiVO>>> = _herois

    fun fetchHerois() {
        _herois.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = repository.fetchCharactersList("")
                val vo = response.data.results.map { heroiDTO ->
                    HeroiVO(
                        id = heroiDTO.id,
                        name = heroiDTO.name,
                        description = heroiDTO.description,
                        image = "${heroiDTO.image.path}/" + "standard_fantastic" + ".${heroiDTO.image.extension}"
                    )
                }
                _herois.value = Result.Success(vo)
            } catch (ex: HttpException) {
                _herois.value = Result.Error
            }
        }
    }


}