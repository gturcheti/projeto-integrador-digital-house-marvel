package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentLoginRegisterBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.LoginViewModel

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
                viewModel.criaUsuario(fragmentLoginRegisterEmailEt.text.toString(),
                    fragmentLoginRegisterNomeEt.text.toString(),
                    fragmentLoginRegisterSenhaEt.text.toString())
            }
        }
    }

    private fun setupObservers() {
        viewModel.novoUsuario.observe(viewLifecycleOwner) {
            viewModel.cadastraUsuario(requireContext())
            findNavController().navigate(R.id.action_loginRegister_to_loginMain)
        }
    }


}