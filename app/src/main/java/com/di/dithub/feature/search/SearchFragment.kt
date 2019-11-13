package com.di.dithub.feature.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.dithub.R
import com.di.dithub.base.BaseFragment
import com.di.dithub.comm.helpers.LoadMoreRecyclerViewScrollListener
import com.di.dithub.feature.LauncherViewModel
import com.di.dithub.feature.search.controller.SearchController
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment() {

    override fun layout(): Int = R.layout.fragment_search

    private val searchController by lazy {
        SearchController {
            val bundle = Bundle().apply {
                putString("URL", it.svnUrl)
            }
            findNavController().navigate(R.id.action_search_to_detail, bundle)
        }.apply {
            addModelBuildListener {
                if (!isLoadMoreAction) rvSearchResult.scrollToPosition(0)
            }
        }
    }

    private var isLoadMoreAction = false
    private lateinit var loadMoreListener: LoadMoreRecyclerViewScrollListener
    private val searchViewModel by currentScope.viewModel(this, SearchViewModel::class)

    private val launcherViewModel by sharedViewModel(LauncherViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launcherViewModel.apply {
            searchKey.observe(this@SearchFragment, Observer {
                isLoadMoreAction = false
                searchViewModel.fetchSearch(it!!)
            })
        }

        searchViewModel.apply {
            repoResult.observe(this@SearchFragment, Observer {
                it?.let { result ->
                    loadMoreListener.hasMoreToLoad = hasMore
                    searchController.setData(result, hasMore)
                }
            })
        }

        loadMoreListener = object : LoadMoreRecyclerViewScrollListener(rvSearchResult.layoutManager as LinearLayoutManager) {
            override fun fetchMore() {
                isLoadMoreAction = true
                val key = launcherViewModel.searchKey.value
                searchViewModel.loadMore(key!!)
            }
        }

        rvSearchResult.addOnScrollListener(loadMoreListener)
        rvSearchResult.setController(searchController)
    }
}