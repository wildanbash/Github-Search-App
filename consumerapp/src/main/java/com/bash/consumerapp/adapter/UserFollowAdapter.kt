package com.bash.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bash.consumerapp.R
import com.bash.consumerapp.models.UserSearch
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class UserFollowAdapter (
    private val userFollow: MutableList<UserSearch>
) : RecyclerView.Adapter<UserFollowAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
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
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int = userFollow.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(userFollow[position])

    fun updateList(it: List<UserSearch>){
        userFollow.clear()
        userFollow.addAll(it)
        notifyDataSetChanged()
    }
}