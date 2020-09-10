package com.bash.githubsearchuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bash.githubsearchuser.R
import com.bash.githubsearchuser.adapter.UserFavoriteAdapter
import com.bash.githubsearchuser.db.entities.UserEntity
import com.bash.githubsearchuser.ui.profile.ProfileActivity
import com.bash.githubsearchuser.util.show
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.layout_error.*

class FavoriteActivity : AppCompatActivity(), FavoriteAdapterInterface {
    private lateinit var viewModel : FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setupRecyclerView()
        setupViewModel()
        getUserFavorite()
        observeUserFavorite()
        setAction()
    }

    private fun getUserFavorite() = viewModel.getFavoriteUserList(this)

    private fun observeUserFavorite() = viewModel.favoriteUserList.observe(this, Observer { handleUsers(it) })

    private fun handleUsers(user: List<UserEntity>) {
        if(user.isEmpty()){
            showError()
        }
        favorite_rv_user.adapter?.let { a ->
            if(a is UserFavoriteAdapter){
                a.updateList(user)
            }
        }
    }

    private fun setupRecyclerView(){
        favorite_rv_user.apply {
            adapter = UserFavoriteAdapter(mutableListOf(), this@FavoriteActivity)
        }
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    private fun setAction(){
        favorite_btn_back.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.getFavoriteUserList(this)
    }

    private fun showError(){
        if(errorLayout.visibility == View.GONE){
            errorLayout.show()
        }

        img_errorImage.setImageResource(R.drawable.il_search_not_found)
        tv_errorTitle.text = getString(R.string.error_title)
        tv_errorMessage.text = getString(R.string.not_found_favorite)
    }

    override fun click(userEntity: UserEntity) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(ProfileActivity.EXTRA_USER, userEntity.login)
        startActivity(intent)
    }
}