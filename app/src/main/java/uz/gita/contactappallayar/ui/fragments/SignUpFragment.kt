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
import uz.gita.contactappallayar.R
import uz.gita.contactappallayar.data.remote.models.request.RegisterRequest
import uz.gita.contactappallayar.databinding.FragmentSignupBinding
import uz.gita.contactappallayar.utils.addListener
import uz.gita.contactappallayar.utils.myApply
import uz.gita.contactappallayar.utils.showToast
import uz.gita.contactappallayar.utils.values
import uz.gita.contactappallayar.viewmodels.SignUpViewModel
import uz.gita.contactappallayar.viewmodels.impl.SignUpViewModelImpl

class SignUpFragment : Fragment(R.layout.fragment_signup) {

    private val binding by viewBinding(FragmentSignupBinding::bind)
    private val viewModel: SignUpViewModel by viewModels<SignUpViewModelImpl>()

    private var boolFirstName = false
    private var boolLastName = false
    private var boolPhoneNumber = false
    private var boolPassword = false
    private var boolConfirmPassword = false

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        inputFirstName.addListener {
            boolFirstName = it.length in 3 until 20
            checK()
        }
        inputLastName.addListener {
            boolLastName = it.length in 3 until 20
            checK()
        }
        inputPhoneNumber.addListener {
            boolPhoneNumber = it.length == 13 && it.toString().startsWith("+998")
            checK()
        }
        inputPassword.addListener {
            Log.d("TTT", "it-> $it  con-> ${inputConfirmPassword.values()}")
            boolPassword = (it.length in 3 until 10) && (it == inputConfirmPassword.values())
            checK()
        }
        inputConfirmPassword.addListener {
            Log.d("TTT", "it-> $it  con-> ${inputPassword.values()}")
            boolConfirmPassword =
                (it.length in 3 until 10) && (it == inputPassword.values())
            checK()
        }

        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        buttonRegister.setOnClickListener {
            viewModel.singUp(
                RegisterRequest(
                    inputFirstName.text.toString(),
                    inputLastName.text.toString(),
                    inputPassword.text.toString(),
                    inputPhoneNumber.text.toString()
                )
            )
        }

        buttonRegister.isEnabled = false



        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
        viewModel.notConnectionLiveData.observe(viewLifecycleOwner, notConnectionObserver)
        viewModel.openVerifyFragmentLiveData.observe(
            this@SignUpFragment,
            openVerifyFragmentObserver
        )
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
    }

    private fun checK() {
        binding.buttonRegister.isEnabled =
            boolFirstName && boolLastName && boolPhoneNumber &&
                    (boolPassword || boolConfirmPassword)
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }

    private val notConnectionObserver = Observer<Unit> {
        showToast("Not internet connection")
    }

    private val openVerifyFragmentObserver = Observer<Unit> {
        val action =
            SignUpFragmentDirections.actionSignupFragmentToVerifyFragment(binding.inputPhoneNumber.values())
        findNavController().navigate(action)
    }

    private val progressObserver = Observer<Boolean> {
        if (it) binding.progress.show()
        else binding.progress.hide()
    }
}