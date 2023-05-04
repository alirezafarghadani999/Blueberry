package alireza.farghadani.blueberry.network;

import com.google.gson.annotations.SerializedName;

public class news {
    @SerializedName("title")
    private String apititle;
    @SerializedName("discription")
    private String apidescription;

    public String getApititle() {
        return apititle;
    }

    public String getApidescription() {
        return apidescription;
    }
}
