package com.tian.common.app.feature.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tian.common.app.feature.room.bean.Student
import com.tian.common.app.feature.room.dao.StudentDao
import com.tian.lib_common.lib_ext.logd

@Database(
    entities = [Student::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    /**
     * 获取 数据库访问 对象
     * 这是必须要实现的函数
     */
    abstract fun studentDao(): StudentDao


    companion object {

        private const val TAG = "Room_AppDatabase"
        private const val DATABASE_NAME = "common.db"
        @Volatile
        private lateinit var instance: AppDatabase

        /**
         * 数据库版本 1 升级到 版本 2 的迁移类实例对象
         */
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                "数据库版本 1 升级到 版本 2".logd(TAG)
                db.execSQL("alter table student add column sex text not null default 'M'")
            }
        }

        /**
         * 数据库版本 2 升级到 版本 3 的迁移类实例对象
         */
        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                "数据库版本 2 升级到 版本 3".logd(TAG)
                db.execSQL("alter table student add column degree integer not null default 1")
            }
        }

        /**
         * 数据库版本 3 升级到 版本 4 的迁移类实例对象
         * 销毁重建策略
         */
        private val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                "数据库版本 3 升级到 版本 4".logd(TAG)
                // 创新临时数据库
                db.execSQL(
                    "CREATE TABLE temp_student (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            "name TEXT," +
                            "age INTEGER NOT NULL," +
                            "sex INTEGER NOT NULL DEFAULT 1," +
                            "degree INTEGER NOT NULL DEFAULT 1)"
                )

                // 拷贝数据
                db.execSQL(
                    "INSERT INTO temp_student (name, age, degree)" +
                            "SELECT name, age, degree FROM student"
                )

                // 删除原始表
                db.execSQL("DROP TABLE student")

                // 将临时表命令为原表表明
                db.execSQL("ALTER TABLE temp_student RENAME TO student")
            }
        }

        fun inst(context: Context): AppDatabase {
            if (!::instance.isInitialized) {
                synchronized(AppDatabase::class) {
                    // 创建数据库
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_2_3)
                        .addMigrations(MIGRATION_3_4)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries() // Room 原则上不允许在主线程操作数据库
                        // 如果要在主线程操作数据库需要调用该函数
                        .build()
                }
            }
            return instance
        }
    }
}