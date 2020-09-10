package com.bash.githubsearchuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bash.githubsearchuser.R
import com.bash.githubsearchuser.models.UserSearch
import com.bash.githubsearchuser.ui.search.SearchAdapterInterface
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class UserSearchAdapter(
    private val userSearch: MutableList<UserSearch>,
    private val mainInterface: SearchAdapterInterface
) : RecyclerView.Adapter<UserSearchAdapter.ViewHolder>(){

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        fun bind(user: UserSearch){
            with(itemView){
                Glide.with(context)
                    .load(user.avatar_url)
                    .circleCrop()
                    .placeholder(android.R.color.darker_gray)
                    .error(android.R.color.darker_gray)
                    .into(itemView.item_user_img_profile)
                item_user_username.text = user.login
                item_user_type.text = user.type
                setOnClickListener{
                    mainInterface.click(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int = userSearch.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(userSearch[position])

    fun updateList(it: List<UserSearch>){
        userSearch.clear()
        userSearch.addAll(it)
        notifyDataSetChanged()
    }
}