package com.example.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRemoteDataSourceImpl : FirebaseRemoteDataSource<FirebaseData> {
    override fun createUser(email: String, password: String, isCreated: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                isCreated(it.isSuccessful)
            }
    }

    override fun addData(collection: String, data: FirebaseData, isAdd: (Boolean) -> Unit) {
        Log.d("결과", data.toString())
        FirebaseStore.addData<FirebaseStore>(collection){
            isAdd(it)
        }
//        FirebaseFirestore.getInstance().collection(collection)
//            .document()
//            .set(data::class.java)
//            .addOnCompleteListener {  //이 리스너는 생략해도 상관은 없단다..
//                isAdd(it.isSuccessful)
//            }
    }

    //    override fun <D> addData(collection: String, isAdd: (Boolean) -> Unit) {
//
//
//        FirebaseFirestore.getInstance().collection("User")?.document("document1")
//            ?.set(D::class.java)
//            ?.addOnCompleteListener {  //이 리스너는 생략해도 상관은 없단다..
//                if (it.isSuccessful) {
//                    Log.d("결과", "데이터 등록 o")
//                } else {
//                    Log.d("결과", "데이터 등록 x")
//                }
//            }
//    }
}

interface FirebaseData

