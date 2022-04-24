package uz.gita.contactappallayar.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.contactappallayar.R
import uz.gita.contactappallayar.databinding.FragmentSplashBinding
import uz.gita.contactappallayar.viewmodels.SplashViewModel
import uz.gita.contactappallayar.viewmodels.impl.SplashViewModelImpl

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openSignInFragmentLiveData.observe(viewLifecycleOwner, openSignInFragmentObserver)
    }

    private val openSignInFragmentObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_splashFragment_to_signinFragment)
    }
}