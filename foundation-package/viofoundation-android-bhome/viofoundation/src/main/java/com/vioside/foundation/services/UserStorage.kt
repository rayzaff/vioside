package com.vioside.foundation.services

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

interface UserStorage {
    fun save(key: String, value: Any)

    fun getString(key: String): String?
    fun getInt(key: String): Int
    fun getFloat(key: String): Float
    fun getLong(key: String): Long
    fun getBoolean(key: String): Boolean
    fun <Key, Value>saveHashMap(key: String, value: HashMap<Key, Value>)
    fun <Key, Value>getHashMap(key: String): HashMap<Key, Value>
    fun removeKey(key: String)

}

class UserSharedPreferences(
    private val context: Context,
    private val storageKey: String
): UserStorage {

    private val pref: SharedPreferences = context.getSharedPreferences(storageKey, 0)

    override fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    override fun getString(key: String): String? {
        return pref.getString(key, null)
    }

    override fun getInt(key: String): Int {
        return pref.getInt(key, 0)
    }

    override fun getFloat(key: String): Float {
        return pref.getFloat(key, 0f)
    }

    override fun getLong(key: String): Long {
        return pref.getLong(key, 0)
    }

    override fun <Key, Value>getHashMap(key: String): HashMap<Key, Value> {
        val type = HashMap<Key, Value>().javaClass
        val data = pref.getString(key, "{}")
        return Gson().fromJson(data, type)
    }

    override fun <Key, Value>saveHashMap(key: String, value: HashMap<Key, Value>) {
        val type = HashMap<Key, Value>().javaClass
        val json = Gson().toJson(value, type)
        pref.edit().apply {
            putString(key, json)
            apply()
        }
    }

    override fun save(key: String, value: Any) {
        pref.edit().apply {
            when(value) {
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
            }
            apply()
        }

    }

    override fun removeKey(key: String) {
        pref.edit().apply {
            remove(key)
            apply()
        }
    }

}