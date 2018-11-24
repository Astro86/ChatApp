package uk.co.victoriajanedavis.chatapp.presentation.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.*
import uk.co.victoriajanedavis.chatapp.R
import uk.co.victoriajanedavis.chatapp.domain.entities.TokenEntity
import uk.co.victoriajanedavis.chatapp.presentation.common.State
import uk.co.victoriajanedavis.chatapp.presentation.common.State.*
import uk.co.victoriajanedavis.chatapp.presentation.ext.invisible
import uk.co.victoriajanedavis.chatapp.presentation.ext.observe
import uk.co.victoriajanedavis.chatapp.presentation.ext.visible
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    @Inject lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModelStateObserver()
        setupLoginButtonClickListener()
        setupSwitchToSignupPageClickListener()

    }

    private fun setupViewModelStateObserver() {
        viewModel.getLoginUserLiveData().observe(viewLifecycleOwner) {
            it?.let(::onStateChanged)
        }
    }

    private fun setupLoginButtonClickListener() {
        loginButton.setOnClickListener {
            viewModel.loginUser(usernameEditText.text.toString(), passwordEditText.text.toString())
        }
    }

    private fun setupSwitchToSignupPageClickListener() {
        signupLinkTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    private fun onStateChanged(state: State<TokenEntity>) = when(state) {
        is ShowContent -> loginSuccessful()
        is ShowLoading -> showLoading()
        is ShowError -> showError(state.message)
    }

    private fun loginSuccessful() {
        findNavController().navigateUp()
    }

    private fun showLoading() {
        disableViews()
        progressBar.visible()
    }

    private fun showError(message: String) {
        enableViews()
        progressBar.invisible()
        Log.e("LoginFragment", message)
    }

    private fun disableViews() {
        setViewsEnabled(false)
    }

    private fun enableViews() {
        setViewsEnabled(true)
    }

    private fun setViewsEnabled(areEnabled: Boolean) {
        usernameEditText.isEnabled = areEnabled
        passwordEditText.isEnabled = areEnabled
        loginButton.isEnabled = areEnabled
        signupLinkTextView.isEnabled = areEnabled
    }
}