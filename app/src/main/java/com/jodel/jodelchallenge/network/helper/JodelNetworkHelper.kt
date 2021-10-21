package com.jodel.jodelchallenge.network.helper

import android.content.Context
import com.jodel.jodelchallenge.network.service.APIService
import com.jodel.jodelchallenge.util.Constant.BASE_URL

class JodelNetworkHelper (context: Context
):BaseNetworkHelper<APIService>(context, APIService::class.java) {

    override fun createHelperService(): APIService {
        return createHelper(BASE_URL)
    }
}