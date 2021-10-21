package com.jodel.jodelchallenge.repository

import com.jodel.jodelchallenge.db.DBManager
import com.jodel.jodelchallenge.model.Query
import com.jodel.jodelchallenge.model.User
import com.jodel.jodelchallenge.model.UserResponse
import com.jodel.jodelchallenge.network.service.APIService
import io.realm.RealmResults

open class UserRepository(
    private val apiService: APIService,
    private val dbManager: DBManager
) {

    suspend fun getUsers(username: String, pageNo: Int): UserResponse {
        return apiService.getUsers(username, pageNo)
    }

    fun saveUsersToDB(users: List<User>?) {
        dbManager.saveSearchResults(users)
    }

    fun markUserFav(user: User?) {
        dbManager.markUserFav(user)
    }

    fun markUserUnFav(user: User?) {
        dbManager.markUserUnFav(user)
    }

    fun getUsersFromDB(): RealmResults<User> {
        return dbManager.getUsers()
    }

    fun getUserFromDB(id: Int?): User? {
        return dbManager.getUser(id)
    }

    fun clearLocalUserDB() {
        return dbManager.clearLocalUserDB()
    }

    fun saveQueryToDB(query: Query) {
        dbManager.saveSearchQuery(query)
    }

    fun getQueryFromDB(): Query? {
        return dbManager.getQuery()
    }
}