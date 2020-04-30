package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.model.TaskModel
import com.example.tasks.service.repository.TaskRepository
import com.example.tasks.service.repository.local.SecurityPreferences

class AllTasksViewModel(application: Application) : AndroidViewModel(application) {
    private val mTaskRepository = TaskRepository(application)

    private val mValidation = MutableLiveData<ValidationListener>()
    var validation: LiveData<ValidationListener> = mValidation

    private val mList = MutableLiveData<List<TaskModel>>()
    var tasks: LiveData<List<TaskModel>> = mList

    private var mTaskFilter = 0

    fun list(taskFilter: Int) {
        mTaskFilter = taskFilter

        val listener = object : APIListener<List<TaskModel>>{
            override fun onFailure(str: String) {
                mList.value = arrayListOf()
                mValidation.value = ValidationListener(str)
            }

            override fun onSuccess(model: List<TaskModel>) {
                mList.value = model
            }
        }

        if (mTaskFilter == TaskConstants.FILTER.ALL){
            mTaskRepository.all(listener)
        } else if (mTaskFilter == TaskConstants.FILTER.NEXT){
            mTaskRepository.nextWeek(listener)
        }else{
            mTaskRepository.overdue(listener)
        }

    }

    fun complete(id: Int){
        updateStatus(id,true)
    }

    fun undo(id: Int){
        updateStatus(id,false)
    }

    private fun updateStatus(id: Int, complete: Boolean){
        mTaskRepository.updateStatus(id,complete, object : APIListener<Boolean>{
            override fun onFailure(str: String) {
            }

            override fun onSuccess(model: Boolean) {
                list(mTaskFilter)
            }
        })
    }

    fun delete(id: Int) {
        mTaskRepository.delete(id,object : APIListener<Boolean>{
            override fun onFailure(str: String) {
                mValidation.value = ValidationListener(str)
            }

            override fun onSuccess(model: Boolean) {
                list(mTaskFilter)
                mValidation.value = ValidationListener()
            }
        })
    }

}