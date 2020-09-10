package com.bash.githubsearchuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bash.githubsearchuser.R
import com.bash.githubsearchuser.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        iv_logo.alpha = 0f
        iv_logo.animate().setDuration(0).alpha(1f).withEndAction{
            val i = Intent(this, SearchActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}