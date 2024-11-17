package com.dino.ander.movieapp.ui.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dino.ander.movieapp.R
import com.dino.ander.movieapp.databinding.FragmentMovieLoginBinding
import com.dino.ander.movieapp.ui.base.BaseFragment
import com.dino.ander.movieapp.ui.utils.UIStateLogin
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieLoginFragment : BaseFragment() {
    private lateinit var binding: FragmentMovieLoginBinding
    private val viewModel by viewModels<MovieLoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etUserName.text.toString(), binding.etPass.text.toString())
        }
        observeUI()
        validateText()
    }

    private fun observeUI() {
        lifecycleScope.launch {
            viewModel.loginFlow.collect {
                when (it) {
                    is UIStateLogin.Loading -> {
                        baseActivity.showProgress()
                    }

                    is UIStateLogin.Success -> {
                        baseActivity.dismissProgress()
                        val action =
                            MovieLoginFragmentDirections.actionMovieLoginFragmentToMovieListFragment()
                        findNavController().navigate(action)
                    }

                    is UIStateLogin.Error -> {
                        baseActivity.dismissProgress()
                        showError(it.message)
                    }

                    is UIStateLogin.Empty -> {
                        baseActivity.dismissProgress()
                    }
                }
            }
        }
    }

    private fun showError(message: Int) {
        Toast.makeText(requireContext(), getString(message), Toast.LENGTH_LONG).show()
    }

    private fun validateText() {
        binding.etUserName.doAfterTextChanged { text ->
            if (text.toString().onlyLetters()) {
                binding.tvErrorUserName.visibility = View.GONE
            } else {
                binding.tvErrorUserName.visibility = View.VISIBLE
                binding.tvErrorUserName.text = getString(R.string.validate_username)
            }
        }
    }

    private fun String.onlyLetters() = all { it.isLetter() }
}