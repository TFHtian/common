package com.tian.common.app.feature.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tian.common.app.feature.room.bean.Student
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/**
 * 数据库访问对象接口 / 使用 @Dao 注解修饰
 * 提供数据库的增删改查方法
 */
@Dao
interface StudentDao: BaseDao<Student> {

    /**
     * 查询数据库表
     */
    @Query("select * from student")
    fun query(): LiveData<List<Student>>

    /**
     * 根据传入的 id 查询数据库表
     * 在注解中使用 :id 调用参数中的 id: Int
     */
    @Query("select * from student where id = :id")
    fun query(id: Int): LiveData<List<Student>>

    /**
     * 根据传入的 id 查询数据库表
     * 在注解中使用 :id 调用参数中的 id: Int
     */
    @Query("select * from student where id = :id")
    fun queryBean(id: Int): Student

    /**
     * 查询数据库表
     */
    @Query("select * from student")
    fun queryList(): List<Student>

    /**
     * 根据传入的 id 查询数据库表
     * 在注解中使用 :id 调用参数中的 id: Int
     */
    @Query("select * from student where id = :id")
    fun queryList(id: Int): List<Student>

    /**
     *  插入记录，如果记录已存在则替换
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCom(student: Student): Completable

    /**
     * 插入记录，返回插入的 ID
     */
    @Insert
    fun insertSin(student: Student): Single<Long>

    /**
     * 根据 id 查找单条记录
     */
    @Query("SELECT * FROM student WHERE id = :id")
    fun findFloById(id: Int): Flowable<Student>

    /**
     * 获取所有记录
     */
    @Query("SELECT * FROM student")
    fun getFloAll(): Flowable<List<Student>>

    /**
     * 根据 id 查找单条记录，返回 Single
     */
    @Query("SELECT * FROM student WHERE id = :id")
    fun findSinById(id: Int): Single<Student>

    /**
     * 获取所有记录，返回 Single
     */
    @Query("SELECT * FROM student")
    fun getSinAll(): Single<List<Student>>
}