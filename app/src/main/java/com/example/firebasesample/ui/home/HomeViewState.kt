package com.example.firebasesample.ui.home

import com.example.firebasesample.base.ViewState
import com.example.firebasesample.data.model.ArticleModel

sealed class HomeViewState : ViewState {
        object RouteAddArticle : HomeViewState()
        data class Message(val message: String) : HomeViewState()
        data class GetArticleList(val list: List<ArticleModel>) : HomeViewState()
    }