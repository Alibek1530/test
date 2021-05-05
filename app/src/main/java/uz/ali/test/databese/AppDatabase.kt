package uz.ali.test.databese

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DataModel::class],version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDataDao(): DataDao

}