package com.leob.news.features.auth.presentation

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.leob.news.features.home.presentation.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, AuthFragment())
                .commit()
        }
    }

    fun openHomeAndFinish() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
