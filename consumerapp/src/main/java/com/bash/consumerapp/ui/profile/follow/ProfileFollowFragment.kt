package com.bash.consumerapp.ui.profile.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bash.consumerapp.R
import com.bash.consumerapp.adapter.UserFollowAdapter
import com.bash.consumerapp.models.UserSearch
import com.bash.consumerapp.util.hide
import com.bash.consumerapp.util.show
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.android.synthetic.main.layout_error.*

class ProfileFollowFragment : Fragment(){
    private lateinit var viewModel : ProfileFollowViewModel

    companion object {
        fun newInstance(key: Int, username: String): ProfileFollowFragment {
            return ProfileFollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(FRAGMENT_KEY, key)
                    putString(FRAGMENT_VALUE, username)
                }
            }
        }

        private const val FRAGMENT_KEY = "fragment_key"
        private const val FRAGMENT_VALUE = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
        observe()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(ProfileFollowViewModel::class.java)
    }

    private fun observe(){
        errorLayout.hide()
        observeState()

        arguments.let {
            val key = it?.getInt(FRAGMENT_KEY)
            val username = it?.getString(FRAGMENT_VALUE)

            if(key == 0){
                if (username != null) {
                    if(username.isNotEmpty() && viewModel.followers.value == null){
                        username.let { user -> viewModel.getFollower(user) }
                    }
                }
                viewModel.followers.observe(viewLifecycleOwner, Observer {follower ->
                    handleFollowers(follower)
                })
            }else{
                if (username != null) {
                    if(username.isNotEmpty() && viewModel.following.value == null){
                        username.let { user -> viewModel.getFollowing(user) }
                    }
                }
                viewModel.following.observe(viewLifecycleOwner, Observer {following ->
                    handleFollowing(following)
                })
            }
        }
    }

    private fun observeState() = viewModel.showProgress.observe(viewLifecycleOwner, Observer { handleState(it) })

    private fun handleState(b: Boolean){
        if(b){
            follow_progress.show()
        }else{
            follow_progress.hide()
        }
    }

    private fun handleFollowing(following: List<UserSearch>){
        if (following.isEmpty()){
            showError(getString(R.string.not_found_following))
        }
        rv_following.adapter?.let { a ->
            if(a is UserFollowAdapter){
                a.updateList(following)
            }
        }
    }

    private fun handleFollowers(followers: List<UserSearch>){
        if (followers.isEmpty()){
            showError(getString(R.string.not_found_follower))
        }
        rv_following.adapter?.let { a ->
            if(a is UserFollowAdapter){
                a.updateList(followers)
            }
        }
    }

    private fun setupRecyclerView(){
        rv_following.apply {
            adapter = UserFollowAdapter(mutableListOf())
        }
    }

    private fun showError(message: String){
        if(errorLayout.visibility == View.GONE){
            errorLayout.show()
        }

        img_errorImage.setImageResource(R.drawable.il_search_not_found)
        tv_errorTitle.text = getString(R.string.error_title)
        tv_errorMessage.text = message
    }

}