package asryab.com.mvvmproject.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import asryab.com.mvvmproject.models.user.Gender;

public class Profile implements Model, Serializable {

    @SerializedName("id")
    public String profileId;
    @SerializedName("username")
    public String username;
    @SerializedName("birth_year")
    public int yearOfBirth;
    @SerializedName("mobile_phone")
    public String phone;
    @SerializedName("gender")
    public Gender gender;
    @SerializedName("country")
    public String country;
    @SerializedName("pin_code")
    public String pincode;
    @SerializedName("user_id")
    public long userId;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("avatarUrl")
    public String avatarUrl;
    @SerializedName("link")
    public String link;
    @SerializedName("bio")
    public String bio;
    @SerializedName("followers_count")
    public int followers_count;
    @SerializedName("followings_count")
    public int followings_count;
    @SerializedName("votes_count")
    public int votes_count;
    @SerializedName("petition_subscriptions_count")
    public int petition_subscriptions_count;
    @SerializedName("posted_topics_count")
    public int posted_topics_count;
    @SerializedName("points")
    public int points;

}
