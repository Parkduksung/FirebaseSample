package com.example.firebasesample.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.firebasesample.R
import com.example.firebasesample.base.BaseFragment
import com.example.firebasesample.base.ViewState
import com.example.firebasesample.databinding.FragmentHomeBinding
import com.example.firebasesample.ui.adapter.ArticleAdapter
import com.example.firebasesample.ui.addarticle.AddArticleActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var articleAdapter: ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        articleAdapter = ArticleAdapter(onItemClicked = { articleModel ->
            homeViewModel.createChatRoom(articleModel)
        })
        binding.articleRecyclerView.adapter = articleAdapter
    }


    private fun initViewModel() {
        binding.viewModel = homeViewModel

        homeViewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            (viewState as? HomeViewState)?.let {
                onChangedHomeViewState(viewState)
            }
        }
    }

    private fun onChangedHomeViewState(viewState: ViewState) {
        when (viewState) {

            is HomeViewState.GetArticleList -> {
                articleAdapter.submitList(viewState.list)
            }

            is HomeViewState.Message -> {
                Snackbar.make(binding.root, viewState.message, Snackbar.LENGTH_LONG).show()
            }

            is HomeViewState.RouteAddArticle -> {
                startActivity(Intent(requireContext(), AddArticleActivity::class.java))
            }

        }
    }
}