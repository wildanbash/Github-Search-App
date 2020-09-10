package com.bash.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bash.consumerapp.R
import com.bash.consumerapp.models.UserDetail
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class UserFavoriteAdapter(
    private val userDetail: MutableList<UserDetail>,
    private val mainInterface: FavoriteAdapterInterface
) : RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(user: UserDetail){
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

    override fun getItemCount(): Int = userDetail.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(userDetail[position])

    fun updateList(it: List<UserDetail>) {
        userDetail.clear()
        userDetail.addAll(it)
        notifyDataSetChanged()
    }

    interface FavoriteAdapterInterface{
        fun click(userDetail: UserDetail)
    }
}