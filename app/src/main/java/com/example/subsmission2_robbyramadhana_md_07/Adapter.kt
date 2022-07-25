package com.example.subsmission2_robbyramadhana_md_07

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subsmission2_robbyramadhana_md_07.databinding.ListUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.subsmission2_robbyramadhana_md_07.Complement.EXTRA_USER

class Adapter: RecyclerView.Adapter<Adapter.UserViewHolder>() {

    private val listUser = ArrayList<UserData>()

    fun setAllData(data: List<UserData>) {
        listUser.apply{
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class UserViewHolder(private val view: ListUserBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(user: UserData) {

            view.apply {
                txtUsername.text = user.username
            }

            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(view.imgAvatar)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, UserDetail::class.java)
                intent.putExtra(EXTRA_USER, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }
}