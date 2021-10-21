package com.jodel.jodelchallenge.ui.vm

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodel.jodelchallenge.R
import com.jodel.jodelchallenge.coroutine.CoroutinesContext
import com.jodel.jodelchallenge.model.Query
import com.jodel.jodelchallenge.model.User
import com.jodel.jodelchallenge.repository.UserRepository
import io.realm.RealmResults
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UserListViewModel(
    private val app: Application,
    private val userRepository: UserRepository,
    private val coroutinesContext: CoroutinesContext
) : ViewModel() {

    val showMesage = ObservableBoolean(false)
    val isRefreshing = ObservableBoolean(false)
    val message = ObservableField<String>()

    val loading = ObservableBoolean(false)
    private val usersLiveData = MutableLiveData<List<User>>()
    private val errorLiveData = MutableLiveData<Throwable>()


    init {
        if (userRepository.getUsersFromDB().size < 1)
            showErrorMessage(app.getString(R.string.label_press_search))
    }

    fun getUsers(query: String, pageNo: Int) {
        setupPreGetUsers(query)
        val queryForApi = "$query in:login".trim()
        viewModelScope.launch(coroutinesContext.Main + handler) {
            val user =
                withContext(coroutinesContext.IO) { userRepository.getUsers(queryForApi, pageNo) }
            val users = user.users
            loading.set(false)
            isRefreshing.set(false)
            usersLiveData.value = users
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        loading.set(false)
        isRefreshing.set(false)
        errorLiveData.value = exception
    }

    private fun setupPreGetUsers(query: String) {
        if (!isRefreshing.get())
            loading.set(true)

        saveQueryToDB(query)
        hideErrorMessage()
    }

    fun showErrorMessage(messageStr: String = "") {
        message.set(messageStr)
        showMesage.set(true)
    }

    fun hideErrorMessage() {
        showMesage.set(false)
    }

    fun clearLocalUserDB() {
        userRepository.clearLocalUserDB()
    }

    fun saveUsersToDB(users: List<User>) {
        return userRepository.saveUsersToDB(users)
    }

    fun markUserFav(id: Int?, isFave: Boolean) {
        val user = userRepository.getUserFromDB(id)
        return if (isFave)
            userRepository.markUserFav(user)
        else
            userRepository.markUserUnFav(user)
    }

    fun getUsersFromDB(): RealmResults<User> {
        return userRepository.getUsersFromDB()
    }

    fun getUserFromDB(id: Int?): User? {
        return userRepository.getUserFromDB(id)
    }

    fun saveQueryToDB(query: String) {
        userRepository.saveQueryToDB(Query(1, query))
    }

    fun getQueryFromDB(): String? {
        return userRepository.getQueryFromDB()?.query
    }

    fun getUsersLiveData(): LiveData<List<User>> {
        return usersLiveData
    }

    fun getErrorLiveData(): LiveData<Throwable> {
        return errorLiveData
    }

}