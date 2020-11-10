package com.lumi.submission2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lumi.submission2.viewmodel.FollowingViewModel
import com.lumi.submission2.adapter.ListUserAdapter
import com.lumi.submission2.R
import kotlinx.android.synthetic.main.activity_main.*

class FollowingFragment : Fragment() {

    private var username: String? = null
    private lateinit var adapter: ListUserAdapter
    private lateinit var followingViewModel: FollowingViewModel

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
        fun newInstance(username: String?) : FollowingFragment {
            val fragment = FollowingFragment()
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
        followingViewModel = ViewModelProvider(activity!!, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        username?.let { followingViewModel.setFollowing(it) }
        activity?.let {
            followingViewModel.getFollowings().observe(it, { user ->
                if (user != null) {
                    adapter.setData(user)
                }
            })
        }
    }

}