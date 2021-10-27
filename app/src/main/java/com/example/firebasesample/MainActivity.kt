package com.example.firebasesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null

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