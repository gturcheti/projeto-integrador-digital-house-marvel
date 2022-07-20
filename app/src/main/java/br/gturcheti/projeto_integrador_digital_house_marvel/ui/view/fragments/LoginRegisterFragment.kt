package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.data.local.entities.User
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentLoginRegisterBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toast
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.LoginViewModel
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.Result

class LoginRegisterFragment : Fragment(R.layout.fragment_login_register) {

    private val viewModel: LoginViewModel by viewModels()
    lateinit var binding: FragmentLoginRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding = FragmentLoginRegisterBinding.bind(requireView())
        with(binding) {
            fragmentLoginRegisterBtn.setOnClickListener {
                viewModel.createUser(
                    User(
                        fragmentLoginRegisterEmailEt.text.toString(),
                        fragmentLoginRegisterNomeEt.text.toString()
                    ),
                    fragmentLoginRegisterSenhaEt.text.toString()
                )
            }
        }
    }

    private fun setupObservers() {
        viewModel.newUser.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> findNavController().navigate(R.id.action_loginRegister_to_loginMain)
                is Result.Error -> requireContext().toast("Algo deu errado")
            }

        }
    }


}