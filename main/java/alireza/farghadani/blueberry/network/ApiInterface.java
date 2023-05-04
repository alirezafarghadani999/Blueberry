package alireza.farghadani.blueberry.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("login.php")
    Call<user_login_st> loginCall(@Query("login_code") String login_code);

    @GET("news.php")
    Call<news> newsCall(@Query("news") String news);

}
