package com.example.subsmission2_robbyramadhana_md_07

import com.example.subsmission2_robbyramadhana_md_07.Complement.EXTRA_USER
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.subsmission2_robbyramadhana_md_07.Complement.TAB_TITLES
import com.example.subsmission2_robbyramadhana_md_07.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class UserDetail : AppCompatActivity(), DescCallback<UserData?> {

    private lateinit var bindingDetail: ActivityUserDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDetail = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(bindingDetail.root)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val username = intent.getStringExtra(EXTRA_USER)

        viewModel.getDetailUser(username).observe(this, {
            when (it) {
                is Desc.Error -> onFailed(it.message)
                is Desc.Loading -> onLoading()
                is Desc.Success -> onSuccess(it.data)
            }
        })

        val pagerAdapter = FollowPagerAdapter(this, username.toString())

        bindingDetail.apply {
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tlMaterial, viewPager) { tlMaterial, position ->
                tlMaterial.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onSuccess(data: UserData?) {
        bindingDetail.apply {
            detailUsername.text = data?.username
            detailName.text = data?.name
            detailLocation.text = data?.location
            detailRepository.text = data?.repository.toString()
            detailCompany.text = data?.company
            detailFollowers.text = data?.follower.toString()
            detailFollowing.text = data?.following.toString()

            Glide.with(this@UserDetail)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(detailAvatar)

            supportActionBar?.title = data?.username

            pbDetail.visibility = invisible
        }
    }

    override fun onLoading() {
        bindingDetail.apply {
            pbDetail.visibility = visible
        }

    }

    override fun onFailed(message: String?) {
        bindingDetail.apply {
            pbDetail.visibility = invisible
        }

    }
}