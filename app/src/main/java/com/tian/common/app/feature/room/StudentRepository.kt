package com.tian.common.app.feature.room

import android.content.Context
import androidx.lifecycle.LiveData
import com.tian.common.app.feature.room.bean.Student
import com.tian.common.app.feature.room.dao.StudentDao
import com.tian.common.app.feature.room.db.AppDatabase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class StudentRepository(context: Context) {

    private var dao: StudentDao

    init {
        val database = AppDatabase.inst(context)
        this.dao = database.studentDao()
    }

    fun insert(student: Student) {
        this.dao.insert(student)
    }

    fun query(): LiveData<List<Student>> {
        return this.dao.query()
    }

    fun update(student: Student) {
        this.dao.update(student)
    }

    fun delete(id: Int) {
        val student = Student(id)
        this.dao.delete(student)
    }

    fun queryStudent(id: Int) : Student {
        return this.dao.queryBean(id)
    }

    fun insertCom(student: Student): Completable {
        return this.dao.insertCom(student)
    }

    fun insertSin(student: Student): Single<Long> {
        return this.dao.insertSin(student)
    }

    fun findFloById(id: Int): Flowable<Student> {
        return this.dao.findFloById(id)
    }

    fun findSinById(id: Int): Single<Student> {
        return this.dao.findSinById(id)
    }
}