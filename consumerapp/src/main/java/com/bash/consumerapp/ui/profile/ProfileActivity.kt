package com.bash.consumerapp.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bash.consumerapp.R
import com.bash.consumerapp.models.UserDetail
import com.bash.consumerapp.util.hide
import com.bash.consumerapp.util.show
import com.bumptech.glide.Glide
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(){

    private var isFavorite: Boolean = false
    private var userDetail: UserDetail? = null
    private lateinit var viewModel : ProfileViewModel
    private lateinit var adapterProfile : ProfileSectionAdapter

    companion object{
        const val EXTRA_USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val user = intent.getParcelableExtra<UserDetail>(EXTRA_USER)
        val username = user?.login ?: ""

        setupViewModel()

        if(user != null){
            getUserCheckFavorite(user.id)
            isFavorite = true
            initDetailUser(user)
        }else{
            getDetail(username)
        }

        setupViewPager(username)
        observe()
        setAction()
    }

    private fun getDetail(username: String) = viewModel.getDetail(username)

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    private fun setupViewPager(username: String){
        adapterProfile = ProfileSectionAdapter(supportFragmentManager, this, username)
        view_pager.adapter = adapterProfile
        tabs.setupWithViewPager(view_pager)
    }

    private fun observe(){
        observeState()
        observeCheckFavorite()
        observeUserDetail()
    }

    private fun observeState() = viewModel.showProgress.observe(this, Observer {handleState(it)})

    private fun observeUserDetail() = viewModel.userDetail.observe(this, Observer {initDetailUser(it)})

    private fun handleState(b: Boolean){
        if(b){
            detail_progress.show()
        }else{
            detail_progress.hide()
        }
    }

    private fun initDetailUser(user: UserDetail){
        detail_repositories.text = user.public_repos.toString()
        detail_follower.text = user.followers.toString()
        detail_following.text = user.following.toString()
        detail_username.text = user.login
        detail_location.text = user.location ?: "-"
        Glide.with(this)
            .load(user.avatar_url)
            .circleCrop()
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .into(detail_iv_profile)
    }

    private fun setAction(){
        detail_btn_back.setOnClickListener{
            onBackPressed()
        }
        detail_floatingAction.setOnClickListener{
            when{
                isFavorite -> {
                    deleteUserFavorite()
                }
                else -> {
                    addUserFavorite()
                }
            }
        }
    }

    // Favorite
    private fun getUserCheckFavorite(id: Int) = viewModel.getUserCheckFavorite(id,this)

    private fun observeCheckFavorite() = viewModel.checkFavorite.observe(this, Observer { handleCheckFavorite(it) })

    private fun handleCheckFavorite(user: UserDetail){
        user.let {
            userDetail = it
        }
        checkIsFavorite()
    }

    private fun deleteUserFavorite(){
        userDetail.let {
            it?.id?.let { id ->
                viewModel.deleteUser(id, this)
                FancyToast.makeText(
                    this,
                    getString(R.string.favorite_success_deleted),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
                isFavorite = false
                checkIsFavorite()
            }
        }
    }

    private fun addUserFavorite(){
        userDetail.let {
            it?.let {
                viewModel.insertFavoriteUsers(it, this)
                FancyToast.makeText(
                    this,
                    getString(R.string.favorite_success_add),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
                isFavorite = true
                checkIsFavorite()
            }
        }
    }

    private fun checkIsFavorite(){
        val drawable =
            if(isFavorite) {
                R.drawable.ic_favorite_favorite_filled
            } else {
                R.drawable.ic_favorite_favorite_unfilled
            }

        detail_floatingAction.setImageResource(drawable)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }
}