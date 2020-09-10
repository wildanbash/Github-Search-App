package com.bash.githubsearchuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bash.githubsearchuser.R
import com.bash.githubsearchuser.db.entities.UserEntity
import com.bash.githubsearchuser.ui.favorite.FavoriteAdapterInterface
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class UserFavoriteAdapter(
    private val userEntity: MutableList<UserEntity>,
    private val mainInterface: FavoriteAdapterInterface
) : RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(user: UserEntity){
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

    override fun getItemCount(): Int = userEntity.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(userEntity[position])

    fun updateList(it: List<UserEntity>) {
        userEntity.clear()
        userEntity.addAll(it)
        notifyDataSetChanged()
    }
}