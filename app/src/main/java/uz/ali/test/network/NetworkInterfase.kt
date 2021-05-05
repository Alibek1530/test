package uz.ali.test.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.ali.test.models.characters.Characters
import uz.ali.test.models.episodes.Result

interface NetworkInterfase {
    @GET("character/")
    suspend fun getCharacterPage(
        @Query("page") page: String
    ): Response<Characters>



    @GET("character/{id}")
    suspend fun getCharacterId(
        @Path("id") id: String
    ): Response<uz.ali.test.models.characters.Result>


    @GET("episode/{id}")
    suspend fun getEpisodeId(
        @Path("id") id: String
    ): Response<Result>


    @GET("location/{id}")
    suspend fun getLocationId(
        @Path("id") id: String
    ): Response<uz.ali.test.models.locations.Result>
}