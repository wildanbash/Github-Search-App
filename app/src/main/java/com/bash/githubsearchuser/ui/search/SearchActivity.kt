package com.bash.githubsearchuser.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bash.githubsearchuser.R
import com.bash.githubsearchuser.adapter.UserSearchAdapter
import com.bash.githubsearchuser.models.UserSearch
import com.bash.githubsearchuser.ui.favorite.FavoriteActivity
import com.bash.githubsearchuser.ui.profile.ProfileActivity
import com.bash.githubsearchuser.ui.setting.SettingsActivity
import com.bash.githubsearchuser.util.hide
import com.bash.githubsearchuser.util.show
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_search_view.*

class SearchActivity : AppCompatActivity(), SearchAdapterInterface {
    private lateinit var viewModel : SearchViewModel
    private var userSearchAdapter = UserSearchAdapter(mutableListOf(), this@SearchActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if(userSearchAdapter.itemCount <= 0){
            showError(R.drawable.il_search, getString(R.string.waiting_recent_title), getString(R.string.waiting_recent_message))
        }
        setupRecyclerView()
        setupViewModel()
        observe()
        setAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private fun observe(){
        viewModel.showProgress.observe(this, Observer { handleLoading(it) })
        viewModel.userList.observe(this, Observer { handleUsers(it) })
    }

    private fun searchUser(){
        if(et_search.text.isNotEmpty()){
                viewModel.getSearchUser(et_search.text.toString())
            }
        }

    private fun handleLoading(b: Boolean){
        if(b){
            search_progress.show()
        }else{
            search_progress.hide()
        }
    }

    private fun handleUsers(users: List<UserSearch>){
        if(users.isEmpty()){
            rv_user.hide()
            showError(R.drawable.il_search_not_found, getString(R.string.error_title), getString(R.string.not_found))
        }else{
            rv_user.show()
            errorLayout.hide()
            rv_user.adapter?.let { a ->
                if(a is UserSearchAdapter){
                    a.updateList(users)
                }
            }
        }
    }

    private fun setupRecyclerView(){
        rv_user.apply {
            adapter = UserSearchAdapter(mutableListOf(), this@SearchActivity)
        }
    }

    private fun setAction(){
        et_search.apply {
            setOnEditorActionListener { _, actionId, _ ->
                var handled = false

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchUser()
                    handled = true
                }
                handled
            }
        }

        ib_search.setOnClickListener {
            searchUser()
        }

        toolbar.apply {
            inflateMenu(R.menu.main_menu)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.action_setting) {
                    val intent = Intent(this@SearchActivity, SettingsActivity::class.java)
                    startActivity(intent)
                }else if(it.itemId == R.id.action_home_favorite){
                    val intent = Intent(this@SearchActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                }
                false
            }
        }
    }

    private fun showError(imageView: Int, title: String, message: String){
        if(errorLayout.visibility == View.GONE){
            errorLayout.show()
        }

        img_errorImage.setImageResource(imageView)
        tv_errorTitle.text = title
        tv_errorMessage.text = message
    }

    override fun click(userSearch: UserSearch) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(ProfileActivity.EXTRA_USER, userSearch.login)
        startActivity(intent)
    }
}