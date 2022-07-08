package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentLoginMainBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.vaiPara
import br.gturcheti.projeto_integrador_digital_house_marvel.preferences.userDataStore
import br.gturcheti.projeto_integrador_digital_house_marvel.preferences.usuarioLogadoPreferences
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities.MainActivity
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

class LoginMainFragment : Fragment(R.layout.fragment_login_main) {

    private val viewModel: LoginViewModel by viewModels()
    lateinit var binding: FragmentLoginMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    fun setupViews() {
        binding = FragmentLoginMainBinding.bind(requireView())
        with(binding) {
            activityLoginRegistrarBtn.setOnClickListener {
                findNavController().navigate(R.id.action_loginMain_to_loginRegister)
            }
            activityLoginLogarBtn.setOnClickListener {
                viewModel.autentica(requireContext(),
                    activityLoginEmail.text.toString(),
                    activityLoginSenha.text.toString())
            }

        }

    }

    fun setupObservers() {
        viewModel.userLogged.observe(viewLifecycleOwner) { usuarioLogado ->
            usuarioLogado?.let {
                lifecycleScope.launch {
                    with(requireContext()) {
                        userDataStore.edit { preferences ->
                            preferences[usuarioLogadoPreferences] = usuarioLogado.email
                            vaiPara(MainActivity::class.java)
                            requireActivity().finish()
                        }
                    }
                }
            }
        }
    }


}

