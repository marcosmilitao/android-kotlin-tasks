package com.example.tasks.service.listener

import com.example.tasks.service.HeaderModel

interface APIListener {

    fun onFailure(str: String)

    fun onSuccess(model: HeaderModel)
}