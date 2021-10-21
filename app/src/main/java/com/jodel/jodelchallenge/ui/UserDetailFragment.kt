package com.jodel.jodelchallenge.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.navArgs
import com.jodel.jodelchallenge.R
import com.jodel.jodelchallenge.databinding.FragmentUserDetailBinding
import com.jodel.jodelchallenge.extension.injectViewModel
import com.jodel.jodelchallenge.ui.base.BaseFragment
import com.jodel.jodelchallenge.ui.vm.UserDetailViewModel
import kotlin.reflect.KClass


class UserDetailFragment : BaseFragment<UserDetailViewModel, FragmentUserDetailBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_user_detail
    override val viewModelClass: KClass<UserDetailViewModel>
        get() = UserDetailViewModel::class

    override fun viewModel() = injectViewModel()

    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initViews(savedInstanceState: Bundle?) {

        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                viewModel.hideLoading()
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {

            }
        }
        binding.webview.loadUrl(viewModel.getUserFromDB(args.userId)?.htmlUrl.orEmpty())
        viewModel.showLoading()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_user_detail, menu)
        val favItem = menu.findItem(R.id.action_fav)
        if(viewModel.getUserFromDB(args.userId)?.isFav == true) {
            favItem.setIcon(R.drawable.ic_star_rate_detail)
        } else {
            favItem.setIcon(R.drawable.ic_star_outline_detail)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_fav) {
            if (viewModel.getUserFromDB(args.userId)?.isFav == true) {
                item.setIcon(R.drawable.ic_star_outline_detail)
                viewModel.markUserFav(args.userId, false)
            } else {
                item.setIcon(R.drawable.ic_star_rate_detail)
                viewModel.markUserFav(args.userId, true)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}