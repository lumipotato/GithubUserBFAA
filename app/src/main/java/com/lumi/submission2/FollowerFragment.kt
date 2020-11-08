package com.lumi.submission2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class FollowerFragment : Fragment() {

    private var username: String? = null
    private lateinit var adapter: FollowAdapter
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
        adapter = FollowAdapter()
        adapter.notifyDataSetChanged()
        rv_users.layoutManager = LinearLayoutManager(activity)
        rv_users.adapter = adapter

        followerViewModel = ViewModelProvider(activity!!, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        username?.let { followerViewModel.setFollower(it) }
        activity?.let {
            followerViewModel.getFollowers().observe(it, { user ->
                if (user != null) {
                    adapter.setData(user)
                }
            })
        }
    }

}