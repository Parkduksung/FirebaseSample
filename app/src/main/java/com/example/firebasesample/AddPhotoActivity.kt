package com.example.firebasesample

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasesample.adapter.PhotoAdapter
import com.example.firebasesample.databinding.ActivityAddPhotoBinding
import com.example.firebasesample.model.ContentDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class AddPhotoActivity : AppCompatActivity() {

    val PICK_IMAGE_FROM_ALBUM = 0

    var photoUri: Uri? = null

    var storage: FirebaseStorage? = null
    var firestore: FirebaseFirestore? = null
    private var auth: FirebaseAuth? = null

    private val photoAdapter by lazy { PhotoAdapter() }

    private lateinit var binding: ActivityAddPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_photo)
        setContentView(binding.root)

        // Firebase storage
        storage = FirebaseStorage.getInstance()
        // Firebase Database
        firestore = FirebaseFirestore.getInstance()
        // Firebase Auth
        auth = FirebaseAuth.getInstance()

//        val photoPickerIntent = Intent(Intent.ACTION_PICK)
//        photoPickerIntent.type = "image/*"
//        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)

        binding.addphotoImage.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
        }

        binding.addphotoBtnUpload.setOnClickListener {
            contentUpload()
        }

        initAdapter()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            //이미지 선택시
            if (resultCode == Activity.RESULT_OK) {
                //이미지뷰에 이미지 세팅
                println(data?.data)
                photoUri = data?.data
                binding.addphotoImage.setImageURI(data?.data)
            } else {
                finish()
            }

        }
    }

    private fun contentUpload() {
        binding.progressBar.visibility = View.VISIBLE

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_.png"
        val storageRef = storage?.reference?.child("images")?.child(imageFileName)
        storageRef?.putFile(photoUri!!)?.addOnSuccessListener { taskSnapshot ->
            binding.progressBar.visibility = View.GONE

            Toast.makeText(
                this, getString(R.string.upload_success),
                Toast.LENGTH_SHORT
            ).show()

            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                //시간 생성
                val contentDTO = ContentDTO()

                taskSnapshot.uploadSessionUri.toString()
                //이미지 주소
                contentDTO.imageUrl = it.toString()
                //유저의 UID
                contentDTO.uid = auth?.currentUser?.uid
                //게시물의 설명
                contentDTO.explain = binding.addphotoEditExplain.text.toString()
                //유저의 아이디
                contentDTO.userId = auth?.currentUser?.email
                //게시물 업로드 시간
                contentDTO.timestamp = System.currentTimeMillis()

                //게시물을 데이터를 생성 및 엑티비티 종료
                firestore?.collection("images")?.document()?.set(contentDTO)

                setResult(Activity.RESULT_OK)
            }

//            val uri = taskSnapshot.storage.downloadUrl
//            //디비에 바인딩 할 위치 생성 및 컬렉션(테이블)에 데이터 집합 생성

        }
            ?.addOnFailureListener {
                binding.progressBar.visibility = View.GONE

                Toast.makeText(
                    this, getString(R.string.upload_fail),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


    private fun initAdapter() {

        binding.rvPhoto.run {
            adapter = photoAdapter
        }

        photoAdapter.setItemClickListener {
            Toast.makeText(this, it.imageUrl.toString(), Toast.LENGTH_SHORT).show()
        }

        getContentDTO()
    }

    private fun getContentDTO() {

        firestore?.collection("images")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                photoAdapter.clear()
                if (querySnapshot == null) return@addSnapshotListener
                for (snapshot in querySnapshot.documents) {
                    photoAdapter.add(snapshot.toObject(ContentDTO::class.java)!!)
                }
            }
    }
}
