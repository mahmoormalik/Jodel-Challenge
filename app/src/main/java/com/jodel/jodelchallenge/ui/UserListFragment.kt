package com.jodel.jodelchallenge.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jodel.jodelchallenge.R
import com.jodel.jodelchallenge.adapter.UserAdapter
import com.jodel.jodelchallenge.adapter.UserItemClickListener
import com.jodel.jodelchallenge.databinding.FragmentUserListBinding
import com.jodel.jodelchallenge.extension.bindTo
import com.jodel.jodelchallenge.extension.injectViewModel

import com.jodel.jodelchallenge.ui.base.BaseFragment
import com.jodel.jodelchallenge.ui.vm.UserListViewModel
import com.jodel.jodelchallenge.util.EndlessScrollEventListener
import kotlinx.android.synthetic.main.fragment_user_list.*
import retrofit2.HttpException
import kotlin.reflect.KClass


class UserListFragment : BaseFragment<UserListViewModel, FragmentUserListBinding>(),
    UserItemClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_user_list
    override val viewModelClass: KClass<UserListViewModel>
        get() = UserListViewModel::class

    override fun viewModel() = injectViewModel()

    private lateinit var endlessScrollingEventListener: EndlessScrollEventListener
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setUpPullToRefresh()
        setupRecyclerView()
    }

    private fun setUpPullToRefresh() {
        binding.pullToRefreshView.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.orange
            )
        )
        binding.pullToRefreshView.setColorSchemeColors(Color.WHITE)

        binding.pullToRefreshView.setOnRefreshListener {
            viewModel.isRefreshing.set(true)
            val query = viewModel.getQueryFromDB().orEmpty()
            fetchResults(query, 1)
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.userRecycler.layoutManager = linearLayoutManager
        setupEndlessScrolling(linearLayoutManager)
        binding.userRecycler.addOnScrollListener(endlessScrollingEventListener)
        adapter = UserAdapter(viewModel.getUsersFromDB(), this)
        binding.userRecycler.adapter = adapter
    }

    private fun setupEndlessScrolling(linearLayoutManager: LinearLayoutManager) {
        endlessScrollingEventListener = object : EndlessScrollEventListener(linearLayoutManager) {
            override fun onLoadMore(pageNum: Int, recyclerView: RecyclerView?) {
                val query = viewModel.getQueryFromDB().orEmpty()
                if (query.isNotEmpty())
                    viewModel.getUsers(query, pageNum)
            }
        }
    }

    override fun bindViewModelData() {

        viewModel.getUsersLiveData().bindTo(this,  { users ->
            viewModel.saveUsersToDB(users)
            if (viewModel.getUsersFromDB().size < 1) {
                endlessScrollingEventListener.reset()
                viewModel.showErrorMessage(getString(R.string.error_no_user))
            }
        })

        viewModel.getErrorLiveData().bindTo(this,  { exception ->
            if (exception is HttpException) {
                val errorCode = exception.code()
                if (errorCode == 403)
                    viewModel.showErrorMessage(getString(R.string.error_api_limit_reached))
                else
                    viewModel.showErrorMessage(getString(R.string.error_generic))

            } else viewModel.showErrorMessage(getString(R.string.error_generic))

            endlessScrollingEventListener.reset()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchItem.setIcon(R.drawable.ic_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(queryStr: String?): Boolean {
                if (queryStr?.length!! >= 3) {
                    fetchResults(queryStr, 1)
                    return true
                }
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    fun fetchResults(query: String, page: Int) {
        endlessScrollingEventListener.reset()
        viewModel.clearLocalUserDB()
        viewModel.getUsers(query, page)
    }

    override fun showUserDetail(id: Int?) {
        val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(
            id!!,
            viewModel.getUserFromDB(id)?.login.orEmpty()
        )
        findNavController().navigate(action)
    }

    override fun markUserFav(id: Int?, isFave: Boolean) {
        viewModel.markUserFav(id, isFave)
    }

}