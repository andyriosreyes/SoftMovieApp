package com.dino.ander.movieapp.ui.base

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    val baseActivity: BaseActivity by lazy {
        activity as BaseActivity
    }
}