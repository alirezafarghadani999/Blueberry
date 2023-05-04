package alireza.farghadani.blueberry.network;
import com.google.gson.annotations.SerializedName;

public class user_login_st {

    @SerializedName("name")
    private String apiname;

    @SerializedName("family")
    private String apifamily;

    @SerializedName("response")
    private String apilogin_code;


    public String getApiname() {
        return apiname;
    }

    public String getApifamily() {
        return apifamily;
    }

    public String getApilogin_code() {
        return apilogin_code;
    }
}
