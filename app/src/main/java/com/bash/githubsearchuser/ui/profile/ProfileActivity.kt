package com.bash.githubsearchuser.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bash.githubsearchuser.R
import com.bash.githubsearchuser.db.entities.UserEntity
import com.bash.githubsearchuser.models.UserDetail
import com.bash.githubsearchuser.ui.widget.UserWidget
import com.bash.githubsearchuser.util.hide
import com.bash.githubsearchuser.util.show
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(){

    private var userEntity: UserEntity? = null
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

        val user = intent.getStringExtra(EXTRA_USER)
        var username = ""
        if(user != null){
            username = user
        }

        setupViewModel()

        if(userDetail == null){
            if(username.isNotEmpty() && viewModel.userDetail.value == null){
                getDetail(username)
            }
        }else{
            val tempUserEntity = Gson().toJson(userDetail)
            val userEntity = Gson().fromJson(tempUserEntity, UserEntity::class.java)
            this.userEntity = userEntity
            isFavorite = true
            detail_floatingAction.show()
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
        observeUserCheckFavorite()
        observeUserDetail()
    }

    private  fun observeState() = viewModel.showProgress.observe(this, Observer {handleState(it)})

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
        detail_location.text = user.location
        Glide.with(this)
            .load(user.avatar_url)
            .circleCrop()
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .into(detail_iv_profile)

        getUserCheckFavorite(user.id)
        userDetail = user
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    // Favorite
    private fun getUserCheckFavorite(id: Int) = viewModel.getUserCheckFavorite(id, this)

    private fun observeUserCheckFavorite() = viewModel.detailUserFavorite.observe(this, Observer { handleUserFavorite(it) })

    private fun handleUserFavorite(user: UserEntity){
        if(user.login != null){
            userEntity = user
            isFavorite = true
            detail_floatingAction.setImageResource(R.drawable.ic_favorite_favorite_filled)
        }else{
            isFavorite = false
            detail_floatingAction.setImageResource(R.drawable.ic_favorite_favorite_unfilled)
        }
        detail_floatingAction.show()
    }

    private fun deleteUserFavorite(){
        when {
            userEntity != null -> {
                viewModel.deleteUser(userEntity!!.id, this)
                detail_floatingAction.setImageResource(R.drawable.ic_favorite_favorite_unfilled)
                userEntity = null
                isFavorite = false
                FancyToast.makeText(
                    this,
                    getString(R.string.favorite_success_deleted),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
            }
        }
    }

    private fun addUserFavorite(){
        when{
            userDetail != null -> {
                val userString = Gson().toJson(userDetail)
                userEntity = Gson().fromJson(userString, UserEntity::class.java)
                when {
                    userEntity != null -> {
                        detail_floatingAction.setImageResource(R.drawable.ic_favorite_favorite_filled)
                        viewModel.insertFavoriteUsers(userEntity!!, this)
                        isFavorite = true
                        FancyToast.makeText(
                            this,
                            getString(R.string.favorite_success_add),
                            FancyToast.LENGTH_SHORT,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                    }
                }
            }
        }
    }
}