package uz.gita.contactappallayar.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.contactappallayar.R
import uz.gita.contactappallayar.data.remote.models.request.VerificationRequest
import uz.gita.contactappallayar.databinding.FragmentVerifyBinding
import uz.gita.contactappallayar.utils.myApply
import uz.gita.contactappallayar.utils.showToast
import uz.gita.contactappallayar.utils.values
import uz.gita.contactappallayar.viewmodels.VerifyViewModel
import uz.gita.contactappallayar.viewmodels.impl.VerifyViewModelImpl

class VerifyFragment : Fragment(R.layout.fragment_verify) {

    private val binding by viewBinding(FragmentVerifyBinding::bind)
    private val viewModel: VerifyViewModel by viewModels<VerifyViewModelImpl>()
    private val args: VerifyFragmentArgs by navArgs()

    private val keyboardList: MutableList<TextView> by lazy { ArrayList() }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        keyboardList.add(keyboard.btZero)
        keyboardList.add(keyboard.btOne)
        keyboardList.add(keyboard.btTwo)
        keyboardList.add(keyboard.btThree)
        keyboardList.add(keyboard.btFour)
        keyboardList.add(keyboard.btFive)
        keyboardList.add(keyboard.btSix)
        keyboardList.add(keyboard.btSeven)
        keyboardList.add(keyboard.btEight)
        keyboardList.add(keyboard.btNine)

        for (i in keyboardList.indices) {
            keyboardList[i].setOnClickListener {
                if (!check())
                    inputSmsCode.setText(inputSmsCode.values() + i.toString())
            }
        }

        keyboard.btConfirm.setOnClickListener {
            if (!check()) return@setOnClickListener
            viewModel.verifyUser(VerificationRequest(inputSmsCode.values(), args.phone))
        }
        keyboard.btClear.setOnClickListener {
            if (inputSmsCode.values().isEmpty()) return@setOnClickListener
            inputSmsCode.setText(
                inputSmsCode.values().substring(0, inputSmsCode.values().length - 1)
            )
        }
        keyboard.btClear.setOnLongClickListener {
            if (inputSmsCode.values().isEmpty()) return@setOnLongClickListener false
            inputSmsCode.setText("")
            return@setOnLongClickListener true
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
        viewModel.notConnectionLiveData.observe(viewLifecycleOwner, notConnectionObserver)
        viewModel.openContactFragmentLiveData.observe(
            this@VerifyFragment,
            openContactFragmentObserver
        )
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val notConnectionObserver = Observer<Unit> {
        showToast("Not internet connection")
    }

    private val openContactFragmentObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_verifyFragment_to_contactsFragment)
    }
    private val progressObserver = Observer<Boolean> {
        if (it) binding.progress.show()
        else binding.progress.hide()
    }

    private fun check(): Boolean = with(binding) {
        return inputSmsCode.values().length == 6
    }

}