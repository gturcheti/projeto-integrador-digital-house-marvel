package br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gturcheti.projeto_integrador_digital_house_marvel.data.MarvelApiRepository
import br.gturcheti.projeto_integrador_digital_house_marvel.data.api.dto.comics.ComicDTO
import br.gturcheti.projeto_integrador_digital_house_marvel.data.preferences.characterDataStore
import br.gturcheti.projeto_integrador_digital_house_marvel.data.preferences.characterIdPreference
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toHttps
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.vo.ComicVO
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ComicViewModel : ViewModel() {

    val IMG_NOT_AVAIBLE = "image_not_available"

    private lateinit var characterID: String

    private val repository: MarvelApiRepository = MarvelApiRepository()

    private val _comicItem: MutableLiveData<Result<List<ComicVO>>> = MutableLiveData()
    val comicItem: LiveData<Result<List<ComicVO>>> = _comicItem

    fun fetchComicByCharacterID(context: Context) {
        _comicItem.value = Result.Loading

        collectCharacterId(context)

        viewModelScope.launch {
            try {
                repository.fetchComicByCharacterId(characterID).let { response ->
                    val vo = mapComics(response.data.results)
                    _comicItem.value = Result.Success(vo)
                }
            } catch (ex: Exception) {
                _comicItem.value = Result.Error
            }
        }
    }

    private fun mapComics(list: List<ComicDTO>): List<ComicVO> {
        return list.filterComics()
            .map { comicDTO ->
                ComicVO(
                    id = comicDTO.id.toString(),
                    title = comicDTO.title,
                    image = "${comicDTO.image.path.toHttps()}" + "/portrait_incredible" + ".${comicDTO.image.extension}",
                    url = "${getUrlComic(comicDTO)}"
                )
            }
    }

    private fun List<ComicDTO>.filterComics(): List<ComicDTO> =
        this.filter { comic ->
            !comic.image.path.contains(IMG_NOT_AVAIBLE)
                    && comic.image.extension.isNotEmpty()
                    && comic.title.isNotEmpty()
                    && comic.urls.isNotEmpty()
        }

    private fun getUrlComic(dto: ComicDTO): String {
        var uri = "https://marvel.com"
        dto.urls.firstOrNull()?.let {
            uri = it.url.toHttps()
        }
        return uri
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

    fun seeMoreOnMarvelSiteIntent(context: Context, uri: String) {
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

}