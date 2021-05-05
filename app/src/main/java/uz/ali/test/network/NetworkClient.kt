package uz.ali.test.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.ali.test.cons.ConstConfig

class NetworkClient {
    private var retrofit: Retrofit? = null

    fun getNetworkClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(ConstConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

}