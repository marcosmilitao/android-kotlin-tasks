package com.example.tasks.service.listener

import com.example.tasks.service.model.HeaderModel

interface APIListener {

    fun onFailure(str: String)

    fun onSuccess(model: HeaderModel)
}