package com.example.firebasesample.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(fragment : Fragment, containerId : Int) {
    supportFragmentManager.beginTransaction().replace(containerId, fragment).commit()
}