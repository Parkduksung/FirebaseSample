package com.example.firebasesample.test

import android.app.Application
import android.util.Log
import com.example.firebasesample.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject
import kotlin.reflect.KClass

@HiltViewModel
class TestViewModel @Inject constructor(app: Application) : BaseViewModel(app) {

    private val auth = FirebaseAuth.getInstance()
    private val store = FirebaseFirestore.getInstance()


    fun test() {


        val testStore =
            store.collection("test1").document("test1")


//        testStore.get().addOnSuccessListener {
//
//            val t = it["list"] as ArrayList<Map<String, Any>>
//
//            t.forEach {
//
//
//            }
////            t.forEach {
////               Log.d("결과", mapToObject(it,Person::class).toString())
////            }
//
//        }

        testStore.update("list", FieldValue.arrayUnion())

//        testStore.update(FieldValue.arrayUnion())
//        val updates = hashMapOf<String, Any>(
//            "list" to FieldValue.delete()
//        )
//
//        testStore.update(updates)
//

//            .get().addOnSuccessListener {
//
//
//                val t = it["list"] as ArrayList<Map<String, String>>
//
//                val q = t.map { Person.from(it) }
//                q.forEach {
//                    Log.d("결과", it.toString())
//                }

//                t.map { it.keys }.forEach {
//                    Log.d("결과", it.toString())
//                }
//
//                t.forEach {
//                    Log.d("결과", it.toString())
//                }
//
//                Log.d("결과", t.size.toString())
//
//                val map = mapOf(
//                    "name" to "Tom Hanley",
//                    "age" to 99
//                )
//
//                Log.d("결과", map.toString())

//            }

//            it["list"] as ArrayList<ArrayList<Person>>

//            Log.d("결과", (it["list"] as ArrayList<ArrayList<Person>>).toString())
//
//            (it["list"] as ArrayList<Person>).forEach {
//                Log.d("결과",it.toString())
//            }
//


//            Log.d("결과", t.toString())

//            Log.d("결과", t?.javaClass.toString())
//            Log.d("결과", t?.javaClass?.name.toString())
//            Log.d("결과", t?.javaClass?.typeName.toString())
//
//            t?.forEach {
//                Log.d("결과", it.javaClass.toString())
//                Log.d("결과", it.javaClass.name.toString())
//                Log.d("결과", it.javaClass.typeName.toString())
//            }

//        }
//
//
//            testStore
//                .update("list", FieldValue.arrayUnion(Person("성",30), Person("a",1234)))
//            .addOnCompleteListener {
//                Log.d("결과", "addOnCompleteListener")
//            }
//            .addOnSuccessListener {
//                Log.d("결과", "addOnSuccessListener")
//            }
//            .addOnFailureListener {
//                Log.d("결과", "addOnFailureListener")
//            }
    }

    fun register() {
        auth.createUserWithEmailAndPassword("qwe@qwr.co", "qwe123")
            .addOnCompleteListener {
                Log.d("결과", "addOnCompleteListener")
            }
            .addOnSuccessListener {
                Log.d("결과", "addOnSuccessListener")
            }
            .addOnFailureListener {
                Log.d("결과", "addOnFailureListener")
            }

    }


    @Serializable
    data class Response<T>(
        val data: Map<String, T>
    )

//    @Serializable
//    data class Person(
//        val name: String,
//        val age: Int
//    )


//    @Serializable
//    data class PersonItem(
//        val list: List<Person>
//    )
}

data class Person(val name: String, val age: Int) {
    companion object {
        fun from(map: Map<String, Any>) = object {
            val name: String by map
            val age: Int by map

            val data = Person(name, age)
        }.data
    }
}

fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>): T {
    //Get default constructor
    val constructor = clazz.constructors.first()

    //Map constructor parameters to map values
    val args = constructor
        .parameters
        .map { it to map.get(it.name) }
        .toMap()

    //return object from constructor call
    return constructor.callBy(args)
}