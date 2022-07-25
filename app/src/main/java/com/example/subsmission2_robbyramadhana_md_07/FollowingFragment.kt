package com.example.subsmission2_robbyramadhana_md_07

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import android.viewbinding.library.fragment.viewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subsmission2_robbyramadhana_md_07.databinding.FragmentFollowerBinding

class FollowingFragment : Fragment(), DescCallback<List<UserData>> {

    companion object {
        private const val KEY_BUNDLE = "USERNAME"

        fun getInstance(username: String): Fragment {
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }

    private val bindingFollowing: FragmentFollowerBinding by viewBinding()
    private lateinit var viewModel: FollowingViewModel
    private lateinit var userAdapter: Adapter
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)

        userAdapter = Adapter()
        bindingFollowing.listUserFollow.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getUserFollowing(username.toString()).observe(viewLifecycleOwner, {
            when (it) {
                is Desc.Error -> onFailed(it.message)
                is Desc.Loading -> onLoading()
                is Desc.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        })
    }

    override fun onSuccess(data: List<UserData>) {
        userAdapter.setAllData(data)

        bindingFollowing.apply {
            messageSearch.visibility = invisible
            pbFollow.visibility = invisible
            listUserFollow.visibility = visible
        }
    }

    override fun onLoading() {
        bindingFollowing.apply {
            messageSearch.visibility = invisible
            pbFollow.visibility = visible
            listUserFollow.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        bindingFollowing.apply {
            if (message == null) {
                messageSearch.text = resources.getString(R.string.following_not_found, username)
                messageSearch.visibility = visible
            } else {
                messageSearch.text = message
                messageSearch.visibility = visible
            }
            pbFollow.visibility = invisible
            listUserFollow.visibility = invisible
        }
    }

}