package com.tian.common.app.feature.room.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * 定义数据库表 Entity 实体 / 同时定义数据库表 和 对应的实体类
 * 设置该数据类对应数据库中的一张数据表, 表名为 student
 * 该数据库表中的数据对应一个 Student 类实例对象
 */
@Entity(tableName = "student")
class Student {
    /**
     * @PrimaryKey 设置主键 autoGenerate 为自增
     * @ColumnInfo name 设置列名称 / typeAffinity 设置列类型
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    var id: Int = 0

    /**
     * 姓名字段
     * 数据库表中的列名为 name
     * 数据库表中的类型为 TEXT 文本类型
     */
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    lateinit var name: String

    /**
     * 年龄字段
     * 数据库表中的列名为 age
     * 数据库表中的类型为 INTEGER 文本类型
     */
    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.INTEGER)
    var age: Int = 0

//    /**
//     * 性别字段
//     * 数据库表中的列名为 sex
//     * 数据库表中的类型为 TEXT 文本类型
//     */
//    @ColumnInfo(name = "sex", typeAffinity = ColumnInfo.TEXT)
//    var sex: String = "M"

    /**
     * 性别字段
     * 数据库表中的列名为 sex
     * 数据库表中的类型为 INTEGER 文本类型
     */
    @ColumnInfo(name = "sex", typeAffinity = ColumnInfo.INTEGER)
    var sex: Int = 0

    /**
     * degree字段
     * 数据库表中的列名为 sex
     * 数据库表中的类型为 INTEGER 文本类型
     */
    @ColumnInfo(name = "degree", typeAffinity = ColumnInfo.INTEGER)
    var degree: Int = 0

    /**
     * 有些属性用于做业务逻辑
     * 不需要插入到数据库中
     * 使用 @Ignore 注解修饰该属性字段
     */
    @Ignore
    lateinit var studentInfo: String

    /**
     * 默认的构造方法给 Room 框架使用
     */
    constructor(id: Int, name: String, age: Int) {
        this.id = id
        this.name = name
        this.age = age
    }

    /**
     * 使用 @Ignore 标签标注后
     * Room 就不会使用该构造方法了
     * 这个构造方法是给开发者使用的
     */
    @Ignore
    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }

    /**
     * 使用 @Ignore 标签标注后
     * Room 就不会使用该构造方法了
     * 这个构造方法是给开发者使用的
     */
    @Ignore
    constructor(id: Int) {
        this.id = id
    }

    override fun toString(): String {
        return "Student(id=$id, name='$name', age=$age, sex=$sex, degree=$degree)"
    }
}