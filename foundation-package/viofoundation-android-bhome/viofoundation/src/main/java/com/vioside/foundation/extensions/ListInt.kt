package com.vioside.foundation.extensions

fun List<Int>?.toListOfString(): List<String>? {
    if(this == null) {
        return null;
    }
    val listOfString = arrayListOf<String>()
    forEach {
        listOfString.add(it.toString())
    }
    return listOfString
}