package com.bash.githubsearchuser.ui.profile

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bash.githubsearchuser.R
import com.bash.githubsearchuser.ui.profile.follow.ProfileFollowFragment

class ProfileSectionAdapter(fm: FragmentManager, context: Context, private val username: String): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val tabTittles = listOf(
        context.getString(R.string.detail_follower),
        context.getString(R.string.detail_following)
    )

    override fun getItem(position: Int): Fragment = ProfileFollowFragment.newInstance(position, username)

    override fun getPageTitle(position: Int): CharSequence? = tabTittles[position]

    override fun getCount(): Int = tabTittles.size

}