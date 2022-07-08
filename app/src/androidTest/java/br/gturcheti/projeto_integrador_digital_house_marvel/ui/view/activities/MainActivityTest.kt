package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)

class MainActivityTest {

    @Test
    fun onCharacterClick_checkIntent() {
        launchActivity<MainActivity>()
        onView(withId(R.id.main_activity_lista_heroi_btn)).perform()
    }
}