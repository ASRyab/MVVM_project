package asryab.com.mvvmproject.models;

import com.google.gson.annotations.SerializedName;

public class Pincode implements Model {

    @SerializedName("id")
    public int id;
    @SerializedName("state")
    public String state;
    @SerializedName("district")
    public String district;
    @SerializedName("taluk")
    public String taluk;
    @SerializedName("area")
    public String area;
    @SerializedName("code")
    public String code;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;

    @Override
    public String toString() {
        return code;
    }
}
