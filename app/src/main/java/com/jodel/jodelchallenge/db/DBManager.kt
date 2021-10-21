package com.jodel.jodelchallenge.db

import android.content.Context
import com.jodel.jodelchallenge.model.Query
import com.jodel.jodelchallenge.model.User
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class DBManager(val context: Context, val realm: Realm) {

    fun saveSearchResults(users: List<User>?) {
        try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(users)
            realm.commitTransaction()
        } catch (e: Exception) {
            realm.cancelTransaction()
        }
    }

    fun markUserFav(user: User?) {
        try {
            realm.beginTransaction()
            user?.isFav = true
            realm.copyToRealmOrUpdate(user)
            realm.commitTransaction()
        } catch (e: Exception) {
            realm.cancelTransaction()
        }
    }

    fun markUserUnFav(user: User?) {
        try {
            realm.beginTransaction()
            user?.isFav = false
            realm.copyToRealmOrUpdate(user)
            realm.commitTransaction()
        } catch (e: Exception) {
            realm.cancelTransaction()
        }
    }

    fun getUsers(): RealmResults<User> {
        return realm.where<User>().findAll()
    }

    fun getUser(id: Int?): User? {
        return realm.where<User>().equalTo("id", id).findFirst()
    }

    fun clearLocalUserDB() {
        try {
            realm.beginTransaction()
            realm.delete(User::class.java)
            realm.commitTransaction()
        } catch (e: Exception) {
            realm.cancelTransaction()
        }
    }


    fun saveSearchQuery(query: Query) {
        try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(query)
            realm.commitTransaction()
        } catch (e: Exception) {
            realm.cancelTransaction()
        }
    }

    fun getQuery(): Query? {
        return realm.where<Query>().findFirst()
    }

    //private fun getRealm(): Realm = realm


}