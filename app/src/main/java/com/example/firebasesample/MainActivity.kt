package com.example.firebasesample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null

    private var firestore: FirebaseFirestore? = null

    //현재 로그인 상태인지 확인하는 리스너.
    private val authListener = FirebaseAuth.AuthStateListener {

        val user = it.currentUser
        if (user != null) {
            Log.d("결과", "로그인 상태 o")
        } else {
            Log.d("결과", "로그인 상태 x")
        }
    }

    override fun onStart() {
        super.onStart()
        auth?.addAuthStateListener(authListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()

    }


    //중복되지는 않는다.
    //입력방식 collection(Collection 이름).document(Document 이름).set(입력할 데이터)
    private fun createData() {

        val userDTO = UserDTO("박덕성", "인천")

        firestore?.collection("User")?.document("document1")?.set(userDTO)
            ?.addOnCompleteListener {  //이 리스너는 생략해도 상관은 없단다..
                if (it.isSuccessful) {
                    Log.d("결과", "데이터 등록 o")
                } else {
                    Log.d("결과", "데이터 등록 x")
                }
            }
    }

    //입력방식 collection(Collection 이름).document(Document 이름).get(입력할 데이터)
    private fun getData() {

        firestore?.collection("User")?.document("document1")?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                val userDTO = it.result?.toObject(UserDTO::class.java)
                println(userDTO)
            }
        }

    }

    //runTransaction 방식 - 여러 클라이언트의 데이터 중복 접근 방지.
    // A가 Documents 에 접근하고 있을때에 B가 Documents 에 접근시 사용할 수 없게 된다.
    private fun runTransaction() {
        var tsDoc = firestore?.collection("User")?.document("document1")
        firestore?.runTransaction { transition ->
            val userDTO = transition.get(tsDoc!!).toObject(UserDTO::class.java)
            userDTO?.name = "AA"
            transition.set(tsDoc, userDTO!!)
        }
    }

    //쿼리 방식 데이터 읽어오기.
    // firebase 는 like 안된다.
    private fun getQuery() {

        //given
        firestore?.run {
            collection("User").document().set(UserDTO("박", "인천", 10))
            collection("User").document().set(UserDTO("덕", "서울", 20))
            collection("User").document().set(UserDTO("성", "부천", 30))
        }


        firestore?.collection("User")?.whereEqualTo("address", "인천")?.get()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (dc in task.result!!.documents) {

                        var userDTO = dc.toObject(UserDTO::class.java)
                        println(userDTO)

                    }
                }
            }

    }

    private fun readQueryWhereGreaterThanData() {

        firestore?.collection("User")?.whereGreaterThan("age", 15)?.get()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    for (dc in task.result!!.documents) {
                        var userDTO = dc.toObject(UserDTO::class.java)
                        println(userDTO)
                    }

                }
            }

    }

    private fun readQueryWhereGreaterThanEqualData() {

        firestore?.collection("User")?.whereGreaterThanOrEqualTo("age", 10)?.get()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    for (dc in task.result!!.documents) {
                        var userDTO = dc.toObject(UserDTO::class.java)
                        println(userDTO)
                    }

                }
            }

    }

    private fun readQueryWhereLessThanData() {

        firestore?.collection("User")?.whereLessThan("age", 15)?.get()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    for (dc in task.result!!.documents) {
                        var userDTO = dc.toObject(UserDTO::class.java)
                        println(userDTO)
                    }

                }
            }

    }

    private fun readQueryWhereLessThanOrEqualToData() {

        firestore?.collection("User")?.whereLessThanOrEqualTo("age", 10)?.get()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    for (dc in task.result!!.documents) {
                        var userDTO = dc.toObject(UserDTO::class.java)
                        println(userDTO)
                    }
                }
            }
    }

    //감지할때 사용.
    // 채팅할때 좋겠군..
    private fun addSnapshotDocument() {
        firestore?.collection("User")?.document("document1")
            ?.addSnapshotListener { documentSnapshot, error ->

                var document = documentSnapshot?.toObject(UserDTO::class.java)

                Log.d("결과", "addSnapshotDocument $document")
            }
    }

    private fun updateData() {

        val map = mutableMapOf<String, Any>()

        map["name"] = "박덕성입니다."

        firestore?.collection("User")?.document("document1")
            ?.update(map)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Log.d("결과", "update 성공.")

                }
            }

    }

    private fun deleteData() {

        firestore?.collection("User")?.document("document1")?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("결과", "삭제 성공.")
                }
            }

    }


    //email 방식이 맞아야 회원가입 가능.
    //중복시 가입 x
    private fun createUserId(email: String, password: String) {
        auth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Log.d("결과", "회원가입성공")
                //성공
            } else {
                Log.d("결과", "회원가입실패")
                //실패
            }
        }
    }

    //로그인시 토근값이 살아있는동안은 계속적으로 로그인이 되어있는것으로 판단.
    private fun loginUserId(email: String, password: String) {
        auth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("결과", "로그인성공")
                //성공
            } else {
                Log.d("결과", "로그인실패")
                //실패
            }
        }
    }

    //검증하고 나서 다시 로그인을 해줘야 true 로 바뀐다.
    private fun isEmailVerifiedCurrentUser(): Boolean =
        auth?.currentUser?.isEmailVerified ?: false

    //검증하라는 메일을 보내는 파라메터
    private fun verifyEmail() {
        auth?.currentUser?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("결과", "검증 파라메터 전송 성공")
            } else {
                Log.d("결과", "검증 파라메터 전송 실패")
            }
        }
    }

    private fun updatePassword(password: String) {
        auth?.currentUser?.updatePassword(password)?.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("결과", "비밀번호 변경 성공")
            } else {
                Log.d("결과", "비밀번호 변경 실패")
            }
        }
    }


    //초기화 메일에서 비밀번호 변경하지 않으면 무시해도 된다.
    //바꿔야지만 반영
    //바꾸고 나서 재로그인 안한 상태로 현재 로그인된 아이디는 유지되는것으로 판단.
    private fun resetPassword(email: String) {
        auth?.sendPasswordResetEmail(email)?.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("결과", "비밀번호 초기화 성공")
            } else {
                Log.d("결과", "비밀번호 초기화 실패")
            }
        }
    }


    // 계정 삭제.
    private fun deleteUser() {
        auth?.currentUser?.delete()?.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("결과", "계정 삭제 성공")
            } else {
                Log.d("결과", "계정 삭제 실패")
            }
        }
    }

    private fun reAuthenticate() {

        val credential = EmailAuthProvider.getCredential("duksung1234@naver.com", "11223344")

        auth?.currentUser?.reauthenticate(credential)?.addOnCompleteListener {

            if (it.isComplete) {
                Log.d("결과", "재인증 성공")
            } else {
                Log.d("결과", "재인증 실패")
            }
        }

    }

    // 로그아웃.
    private fun logout() {
        auth?.signOut()
    }

    override fun onDestroy() {
        super.onDestroy()
        auth?.removeAuthStateListener(authListener)
    }
}

data class UserDTO(var name: String? = null, var address: String? = null, var age: Int? = null)