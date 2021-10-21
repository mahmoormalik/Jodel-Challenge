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

class UserDetailViewModel(
    private val app: Application,
    private val userRepository: UserRepository
) : ViewModel() {

    val loading = ObservableBoolean(false)

    fun showLoading() {
        loading.set(true)
    }

    fun hideLoading() {
        loading.set(false)
    }

    fun getUserFromDB(id: Int?): User? {
        return userRepository.getUserFromDB(id)
    }

    fun markUserFav(id: Int?, isFave: Boolean) {
        val user = userRepository.getUserFromDB(id)
        return if (isFave)
            userRepository.markUserFav(user)
        else
            userRepository.markUserUnFav(user)
    }
}