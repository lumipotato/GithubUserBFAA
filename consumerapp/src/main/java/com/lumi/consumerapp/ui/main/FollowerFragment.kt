package com.lumi.consumerapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lumi.consumerapp.R
import com.lumi.consumerapp.adapter.ListUserAdapter
import com.lumi.consumerapp.db.UserEntity
import com.lumi.consumerapp.viewmodel.FollowerViewModel
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment() {

    private var username: String? = null
    private lateinit var adapter: ListUserAdapter
    private lateinit var followerViewModel: FollowerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)

    }

    companion object {
        private const val ARG_USERNAME = "username"
        fun newInstance(username: String?) : FollowerFragment {
            val fragment = FollowerFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_users.setHasFixedSize(true)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        rv_users.layoutManager = LinearLayoutManager(activity)
        rv_users.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserEntity) {
                val moveIntent = Intent(activity, Detail::class.java)
                moveIntent.putExtra(Detail.EXTRA_USER, data)
                startActivity(moveIntent)
            }
        })

        followerViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
            FollowerViewModel::class.java)
        username?.let { followerViewModel.setFollower(it) }
        activity?.let {
            followerViewModel.getFollowers().observe(it, { user ->
                if (user != null) {
                    adapter.mData = user
                }
            })
        }
    }

}