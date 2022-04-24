package uz.gita.contactappallayar.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.contactappallayar.R
import uz.gita.contactappallayar.data.remote.models.request.LoginRequest
import uz.gita.contactappallayar.databinding.FragmentSigninBinding
import uz.gita.contactappallayar.utils.addListener
import uz.gita.contactappallayar.utils.myApply
import uz.gita.contactappallayar.utils.showToast
import uz.gita.contactappallayar.utils.values
import uz.gita.contactappallayar.viewmodels.SignInViewModel
import uz.gita.contactappallayar.viewmodels.impl.SignInViewModelImpl


class SignInFragment : Fragment(R.layout.fragment_signin) {

    private val binding by viewBinding(FragmentSigninBinding::bind)
    private val viewModel: SignInViewModel by viewModels<SignInViewModelImpl>()
    private var phoneBool: Boolean = false
    private var passwordBool: Boolean = false


    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        editTextPassword.addListener {
            passwordBool = it.length in 3..10
            check()
        }

        editTextPhone.addListener {
            phoneBool = it.length == 13 && it.startsWith("+998")
            check()
        }

        buttonLogin.isEnabled = false

        buttonLogin.setOnClickListener {
            Log.d("TTT", editTextPassword.values())
            Log.d("TTT", editTextPhone.values())
            viewModel.signInUser(
                LoginRequest(
                    editTextPhone.values(),
                    editTextPassword.values()
                )
            )
        }

        buttonRegister.setOnClickListener {
            viewModel.signUpUser()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
        viewModel.notConnectionLiveData.observe(viewLifecycleOwner, notConnectionObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.openContractFragmentLiveData.observe(
            this@SignInFragment,
            openContractFragmentObserver
        )
        viewModel.openSignUpFragmentLiveData.observe(this@SignInFragment, openSignUpFragmentObserver)
    }

    private fun check() {
        binding.buttonLogin.isEnabled = passwordBool && phoneBool
    }


    private val errorObserver = Observer<String> {
        showToast(it)
    }

    private val notConnectionObserver = Observer<Unit> {
        showToast("Internet mavjud emas")
    }

    private val progressObserver = Observer<Boolean> {
        if (it) binding.progress.show()
        else binding.progress.hide()
    }

    private val openContractFragmentObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_signinFragment_to_contactsFragment)
    }

    private val openSignUpFragmentObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_signinFragment_to_signupFragment)
    }


}