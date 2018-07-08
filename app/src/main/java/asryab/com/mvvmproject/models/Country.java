package asryab.com.mvvmproject.models;

import com.google.gson.annotations.SerializedName;

public class Country implements Model {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("code")
    public String iso;
    @SerializedName("dial_code")
    public String phoneCode;

    @Override
    public String toString() {
        return name;
    }
}
