package com.example.firebasesample.test

import android.app.Application
import android.util.Log
import com.example.firebasesample.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.decodeFromStream
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

@HiltViewModel
class TestViewModel @Inject constructor(app: Application) : BaseViewModel(app) {

    private val auth = FirebaseAuth.getInstance()
    private val store = FirebaseFirestore.getInstance()


    fun test() {


//        val testStore =
//            store.collection("test1").document("test1")

//        val citiesRef = store.collection("cities")
//
//        val ggbData = mapOf(
//            "name" to "Golden Gate Bridge",
//            "type" to "bridge"
//        )
//        citiesRef.document("SF").collection("landmarks").add(ggbData)
//
//        val lohData = mapOf(
//            "name" to "Legion of Honor",
//            "type" to "museum"
//        )
//        citiesRef.document("SF").collection("landmarks").add(lohData)
//
//        val gpData = mapOf(
//            "name" to "Griffth Park",
//            "type" to "park"
//        )
//        citiesRef.document("LA").collection("landmarks").add(gpData)
//
//        val tgData = mapOf(
//            "name" to "The Getty",
//            "type" to "museum"
//        )
//        citiesRef.document("LA").collection("landmarks").add(tgData)
//
//        val lmData = mapOf(
//            "name" to "Lincoln Memorial",
//            "type" to "memorial"
//        )
//        citiesRef.document("DC").collection("landmarks").add(lmData)
//
//        val nasaData = mapOf(
//            "name" to "National Air and Space Museum",
//            "type" to "museum"
//        )
//        citiesRef.document("DC").collection("landmarks").add(nasaData)
//
//        val upData = mapOf(
//            "name" to "Ueno Park",
//            "type" to "park"
//        )
//        citiesRef.document("TOK").collection("landmarks").add(upData)
//
//        val nmData = mapOf(
//            "name" to "National Musuem of Nature and Science",
//            "type" to "museum"
//        )
//        citiesRef.document("TOK").collection("landmarks").add(nmData)
//
//        val jpData = mapOf(
//            "name" to "Jingshan Park",
//            "type" to "park"
//        )
//        citiesRef.document("BJ").collection("landmarks").add(jpData)
//
//        val baoData = mapOf(
//            "name" to "Beijing Ancient Observatory",
//            "type" to "musuem"
//        )
//        citiesRef.document("BJ").collection("landmarks").add(baoData)

//        store.collectionGroup("landmarks").get().addOnSuccessListener {
//            //전체 가져오기.
//            it.documents.forEach {
//                Log.d("결과",it.data.toString() )
//            }
//        }
//
//        store.collectionGroup("landmarks").whereEqualTo("type", "museum").get()
//            .addOnSuccessListener { queryDocumentSnapshots ->
//                Log.d("결과",queryDocumentSnapshots.toString())
//            }

        //set
//        testStore.set(emptyMap<String,Person>())

        //update
//        testStore.update("list", FieldValue.arrayUnion(Person("asdf", 1), Person("as", 2)))


        //get all style1
//        testStore.get().addOnSuccessListener {
//
//            val getTime = measureTimeMillis {
//
//                val list = it["list"] as ArrayList<Map<String, Any>>
//
//                list.forEach {
//                    Log.d("결과", mapToObject(it, Person::class).toString())
//                }
//
//            }
//
//            Log.d("결과", getTime.toString())
//        }


        //get all style2
        //23초
//        testStore.get().addOnSuccessListener {
//
//            val getTime = measureTimeMillis {
//                val list = it["list"] as ArrayList<Map<String, Any>>
//
//                val convertList = list.map { Person.from(it) }
//
//                convertList.forEach {
//                    Log.d("결과", it.toString())
//                }
//            }
//
//            Log.d("결과", getTime.toString())
//        }

        //get all style3
//        testStore.get().addOnSuccessListener { result ->
//
//            val getTime = measureTimeMillis {
//                val list = result.toObject(PersonEntryDoc::class.java)?.list
//
//                list?.forEach {
//                    Log.d("결과", it.toString())
//                }
//            }
//            Log.d("결과", getTime.toString())
//        }
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

@Serializable
data class Person(val name: String? = "", val age: Long? = -1) {
    companion object {
        fun from(map: Map<String, Any>) = object {
            val name: String by map
            val age: Long by map

            val data = Person(name, age)
        }.data
    }
}

fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>): T {
    //Get default constructor
    val constructor = clazz.constructors.first()

    //Map constructor parameters to map values
    val args = constructor.parameters.associateWith { map[it.name] }

    //return object from constructor call
    return constructor.callBy(args)
}

data class PersonEntryDoc(
    var list: MutableList<Person>? = null
)