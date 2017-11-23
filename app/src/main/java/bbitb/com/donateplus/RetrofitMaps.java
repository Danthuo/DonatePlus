package bbitb.com.donateplus;

/**
 * Created by Anne Wangari18 on 11/22/2017.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitMaps {

    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
    @GET("https://maps.googleapis.com/maps/api/place/search/json?sensor=false&key=AIzaSyBkoIfbi99MOoTG6hLuSUPpvMRZVc-d3pg")
    Call<Example> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);

}
