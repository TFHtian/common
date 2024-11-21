package com.tian.common.app.feature.room

import androidx.lifecycle.MutableLiveData
import com.tian.common.app.App
import com.tian.common.app.feature.room.bean.Student
import com.tian.common.app.feature.room.db.DataBaseHelper
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel
import com.tian.lib_common.lib_ext.launch
import com.tian.lib_common.lib_ext.logd

class RoomViewModel: BaseViewModel() {

    private var repository: StudentRepository? = null
    var studentContent: MutableLiveData<String> = MutableLiveData()

    init {
        repository = StudentRepository(App.instance)
    }

    fun insertStudent() {
        launch({
            val student = Student(1,"TFH", 18)
            repository?.insert(student)
        }, {
            "插入成功".logd()
        }, {
            it.message?.logd()
        })
    }

    fun queryStudent() {
        launch({
            repository?.queryStudent(1)
        }, {
            studentContent.value = it.toString()
        }, {
            it.message?.logd()
        })
    }

    fun insertRxStudent() {
//        val student = Student(2,"TFH", 22)
//        repository?.insertCom(student)?.let {
//            DataBaseHelper.instance.addDisposable(it) {
//                // 处理完成事件
//                "插入成功".logd()
//            }
//        }
        val student = Student(3,"TFH", 22)
        repository?.insertSin(student)?.let {
            DataBaseHelper.instance.addDisposable(it) { result ->
                // 处理结果
                result.toString().logd()
            }
        }
    }

    fun queryRxStudent() {
//        repository?.findFloById(2)?.let {
//            DataBaseHelper.instance.addDisposable(it) { result ->
//                // 处理结果
//                studentContent.value = result.toString()
//            }
//        }
        repository?.findSinById(3)?.let {
            DataBaseHelper.instance.addDisposable(it) { result ->
                // 处理结果
                studentContent.value = result.toString()
            }
        }
    }
}