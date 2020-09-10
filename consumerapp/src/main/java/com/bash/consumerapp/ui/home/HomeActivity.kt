package com.bash.consumerapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bash.consumerapp.R
import com.bash.consumerapp.adapter.UserFavoriteAdapter
import com.bash.consumerapp.models.UserDetail
import com.bash.consumerapp.ui.profile.ProfileActivity
import com.bash.consumerapp.util.show
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_error.*

class HomeActivity : AppCompatActivity(), UserFavoriteAdapter.FavoriteAdapterInterface{

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupRecyclerView()
        setupViewModel()
        getUserFavorite()
        observeUserFavorite()
        setAction()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun setupRecyclerView(){
        home_rv_favorite.apply {
            adapter = UserFavoriteAdapter(mutableListOf(), this@HomeActivity)
        }
    }

    private fun getUserFavorite() = viewModel.getFavoriteUsersList(this)

    private fun observeUserFavorite() = viewModel.favoriteUserList.observe(this, Observer { handleUsers(it) })

    private fun handleUsers(user: List<UserDetail>) {
        if(user.isEmpty()){
            showError()
        }
        home_rv_favorite.adapter?.let { a ->
            if(a is UserFavoriteAdapter){
                a.updateList(user)
            }
        }
    }

    private fun showError(){
        if(errorLayout.visibility == View.GONE){
            errorLayout.show()
        }

        img_errorImage.setImageResource(R.drawable.il_search_not_found)
        tv_errorTitle.text = getString(R.string.error_title)
        tv_errorMessage.text = getString(R.string.not_found_favorite)
    }

    private fun setAction(){
        home_toolbar.apply {
            inflateMenu(R.menu.main_menu)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.change_language) {
                    startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                }
                false
            }
        }
    }

    override fun onRestart() {
        viewModel.getFavoriteUsersList(this)
        super.onRestart()
    }

    override fun click(userDetail: UserDetail) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(ProfileActivity.EXTRA_USER, userDetail)
        startActivity(intent)
    }
}