package com.tian.common.app.feature.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {

    /**
     * 向数据库表中插入元素
     *
     * onConflict
     *  -OnConflictStrategy.IGNORE：忽略冲突，不插入新数据，也不会更新已有记录。
     *  -OnConflictStrategy.ABORT（默认）：抛出 SQLiteConstraintException，终止当前插入操作。
     *  -OnConflictStrategy.REPLACE（如上所示）：替换已有记录，相当于执行 INSERT OR REPLACE 语句。
     *  -OnConflictStrategy.FAIL：等同于 ABORT，保留以兼容旧版本 Room。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bean: T)

    /**
     * 从数据库表中删除元素
     */
    @Delete
    fun delete(bean:T)

    /**
     * 修改数据库表元素
     */
    @Update
    fun update(bean: T)
}