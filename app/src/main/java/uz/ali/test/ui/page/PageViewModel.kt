package uz.ali.test.ui.page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import uz.ali.test.databese.DataModel
import uz.ali.test.network.NetworkClient
import uz.ali.test.network.NetworkInterfase

class PageViewModel : ViewModel() {
    private var networkInterfase =
        NetworkClient().getNetworkClient()!!.create(NetworkInterfase::class.java)

    var getNetworkError = MutableLiveData<String>()
    var loading = MutableLiveData<Boolean>()

    var name=MutableLiveData<String>()
    var statuss=MutableLiveData<String>()
    var human=MutableLiveData<String>()
    var location=MutableLiveData<String>()
    var locationEnd=MutableLiveData<String>()
    var imageUrl=MutableLiveData<String>()

    var key=MutableLiveData<String>()
    var episod0=MutableLiveData<String>()
    var id="10"

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }


    fun setPage() {
        id=episod0.value!!.reversed()
        id=id.substringBefore("/")
        id=id.reversed()



        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var responce = networkInterfase.getCharacterId(key.value.toString())

            withContext(Dispatchers.Main) {
                if (responce.isSuccessful) {
                    loading.value = false
                    name.value=responce.body()!!.name
                    statuss.value=responce.body()!!.status
                    human.value=responce.body()!!.species
                    imageUrl.value=responce.body()!!.image
                    location.value=responce.body()!!.location.name

                } else {
                    onError("error network")
                }
            }
        }

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var responce = networkInterfase.getEpisodeId(id)

            withContext(Dispatchers.Main) {
                if (responce.isSuccessful) {
                    loading.value = false
                    locationEnd.value=responce.body()!!.name
                } else {
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