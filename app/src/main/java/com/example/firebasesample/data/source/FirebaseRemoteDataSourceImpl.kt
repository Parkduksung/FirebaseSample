package com.example.firebasesample.data.source

import com.example.firebasesample.constant.DBKey
import com.example.firebasesample.data.model.ArticleModel
import com.example.firebasesample.ui.chatlist.ChatListItem
import com.example.firebasesample.ui.home.HomeViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseRemoteDataSourceImpl @Inject constructor() : FirebaseRemoteDataSource {

    private var articleDB: DatabaseReference = Firebase.database.reference.child(DBKey.DB_ARTICLES)
    private var userDB: DatabaseReference = Firebase.database.reference.child(DBKey.DB_USERS)
    private val auth: FirebaseAuth by lazy { Firebase.auth }


    override fun createChatRoom(articleModel: ArticleModel, callback: (String) -> Unit) {
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

                callback("채팅방이 생성되었습니다. 채팅탭에서 확인해주세요.")
            } else {
                callback("내가 올린 아이템입니다")
                // 내가 올린 아이템
            }
        } else {
            // 로그인을 안한 상태
            callback("로그인 후 사용해주세요")
        }
    }

    override fun isLoginUser(): Boolean =
        auth.currentUser != null

    override fun setChildEventListener(listener: ChildEventListener) {
        articleDB.addChildEventListener(listener)
    }

    override fun removeChildEventListener(listener: ChildEventListener) {
        articleDB.removeEventListener(listener)
    }
}