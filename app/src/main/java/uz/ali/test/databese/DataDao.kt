package uz.ali.test.databese

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Query("select * from DataModel")
    fun getAllData(): List<DataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dataModel: DataModel?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(listdataModel: List<DataModel>?)
}