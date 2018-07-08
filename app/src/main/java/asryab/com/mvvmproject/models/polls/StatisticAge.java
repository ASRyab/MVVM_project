package asryab.com.mvvmproject.models.polls;

import com.google.gson.annotations.SerializedName;

import asryab.com.mvvmproject.models.Model;

public final class StatisticAge implements Model {

    @SerializedName("18-24")
    public float age_18_24;

    @SerializedName("25-32")
    public float age_25_32;

    @SerializedName("33-42")
    public float age_33_42;

    @SerializedName("43-50")
    public float age_43_50;

    @SerializedName(">50")
    public float age_more_50;

}
