package com.example.firebasesample.ui.home

import android.app.Application
import com.example.firebasesample.base.BaseViewModel
import com.example.firebasesample.constant.DBKey
import com.example.firebasesample.data.model.ArticleModel
import com.example.firebasesample.ui.chatlist.ChatListItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : BaseViewModel(app = application) {

    private var articleDB: DatabaseReference = Firebase.database.reference.child(DBKey.DB_ARTICLES)
    private var userDB: DatabaseReference = Firebase.database.reference.child(DBKey.DB_USERS)
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    private val articleList = mutableListOf<ArticleModel>()

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return
            articleList.add(articleModel)
            viewStateChanged(HomeViewState.GetArticleList(articleList))
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }

    init {
        articleDB.addChildEventListener(listener)
        articleList.clear()
    }

    fun createChatRoom(articleModel: ArticleModel) {
        if (auth.currentUser != null) {
            // 로그인을 한 상태
            if (auth.currentUser!!.uid != articleModel.sellerId) {

                val chatRoom = ChatListItem(
                    buyerId = auth.currentUser!!.uid,
                    sellerId = articleModel.sellerId,
                    itemTitle = articleModel.title,
                    key = System.currentTimeMillis()
                )

                userDB.child(auth.currentUser!!.uid)
                    .child(DBKey.CHILD_CHAT)
                    .push()
                    .setValue(chatRoom)

                userDB.child(articleModel.sellerId)
                    .child(DBKey.CHILD_CHAT)
                    .push()
                    .setValue(chatRoom)

                viewStateChanged(HomeViewState.Message("채팅방이 생성되었습니다. 채팅탭에서 확인해주세요."))
            } else {
                // 내가 올린 아이템
                viewStateChanged(HomeViewState.Message("내가 올린 아이템입니다"))
            }
        } else {
            // 로그인을 안한 상태
            viewStateChanged(HomeViewState.Message("로그인 후 사용해주세요"))
        }
    }

    fun addArticle() {
        if (auth.currentUser != null) {
            viewStateChanged(HomeViewState.RouteAddArticle)
        } else {
            viewStateChanged(HomeViewState.Message("로그인 후 사용해주세요"))
        }
    }

}