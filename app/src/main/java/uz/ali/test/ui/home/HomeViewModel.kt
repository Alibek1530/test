package uz.ali.test.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import uz.ali.test.app.App
import uz.ali.test.databese.AppDatabase
import uz.ali.test.databese.DataDao
import uz.ali.test.databese.DataModel
import uz.ali.test.models.characters.Characters
import uz.ali.test.network.NetworkClient
import uz.ali.test.network.NetworkInterfase

class HomeViewModel : ViewModel() {
    private var countPage = 1
    private lateinit var list: ArrayList<DataModel>

    private var networkInterfase =
        NetworkClient().getNetworkClient()!!.create(NetworkInterfase::class.java)

    private var roomDao: DataDao

    init {
        roomDao = App.getAppDB().getDataDao()
    }

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }


    var getCharacterList = MutableLiveData<List<DataModel>>()
    var loading = MutableLiveData<Boolean>()
    var getNetworkError = MutableLiveData<String>()

    fun start() {
        next()
    }

    fun next() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var responce = networkInterfase.getCharacterPage(countPage.toString())
            countPage++
            if (responce.isSuccessful) {
                list = arrayListOf()

                responce.body()!!.results.forEach {
                    list.add(
                        DataModel(
                            it.id,
                            it.name,
                            it.status,
                            it.species,
                            it.type,
                            it.gender,
                            it.image,
                            it.url,
                            it.created,
                            it.location.name,
                            it.episode[0]
                        )
                    )
                }
                roomDao.insertAll(list)
            }

            withContext(Dispatchers.Main) {
                if (responce.isSuccessful) {
                    getCharacterList.value = list
                    loading.value = false

                } else {
                    getCharacterList.value = roomDao.getAllData()
                    onError("error network")
                }
            }
        }
    }

    private fun onError(message: String) {
        getNetworkError.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}