package com.dino.ander.movieapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dino.ander.movieapp.ui.dialogs.closeProgressDialog
import com.dino.ander.movieapp.ui.dialogs.showProgressDialog

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showProgress() {
        showProgressDialog()
    }

    fun dismissProgress() {
        if (!isFinishing) {
            closeProgressDialog()
        }
    }


}