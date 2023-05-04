package alireza.farghadani.blueberry.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static final String BASE_URL = "http://blueberry.freehost.io/blueberry_app/";
    public static Retrofit myRetrofit = null;

    public static Retrofit getAPI() {

        if (myRetrofit == null) {

            myRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return myRetrofit;

    }

}
