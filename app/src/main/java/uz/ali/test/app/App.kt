package uz.ali.test.app

import android.app.Application
import androidx.room.Room
import uz.ali.test.databese.AppDatabase

class App : Application() {
    companion object {
        private lateinit var db: AppDatabase

        fun getAppDB(): AppDatabase = db
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, AppDatabase::class.java, "data.db")
            .allowMainThreadQueries().build()

    }
}