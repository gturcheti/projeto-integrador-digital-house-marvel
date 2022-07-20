package br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.gturcheti.projeto_integrador_digital_house_marvel.R
import br.gturcheti.projeto_integrador_digital_house_marvel.databinding.FragmentLoginMainBinding
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.toast
import br.gturcheti.projeto_integrador_digital_house_marvel.extensions.vaiPara
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.view.activities.MainActivity
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.LoginViewModel
import br.gturcheti.projeto_integrador_digital_house_marvel.ui.viewmodels.Result
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider

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
                viewModel.loginAuth(
                    fragmentLoginEmailIt.text.toString(),
                    fragmentLoginPasswordIt.text.toString()
                )
            }
            fragmentLoginGoogleSignInBtn.setOnClickListener {
                startActivityForResult(
                    viewModel.signInWithGoogleIntent(requireActivity()),
                    viewModel.GOOGLE_REQUEST_CODE
                )
            }

        }

    }

    fun setupObservers() {
        viewModel.userSignIn.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> onSuccess()
                is Result.Error -> requireContext().toast("Algo deu errado")
            }
        }
    }

    private fun onSuccess() {
        with(requireContext()) {
            vaiPara(MainActivity::class.java)
            requireActivity().finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == viewModel.GOOGLE_REQUEST_CODE) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            val credencial = GoogleAuthProvider.getCredential(accountTask.result.idToken,null)
            viewModel.loginGoogle(credencial, accountTask)
        }
    }
}

