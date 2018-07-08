package asryab.com.mvvmproject.models.petitions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import asryab.com.mvvmproject.models.Model;
import asryab.com.mvvmproject.models.polls.Level;

public class Petition implements Model, Serializable {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("owner_id")
    @Expose
    public int ownerId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("long_description")
    @Expose
    public String longDescription;
    @SerializedName("short_description")
    @Expose
    public String shortDescription;
    @SerializedName("level")
    @Expose
    public int level;
    @SerializedName("region")
    @Expose
    public String region;
    @SerializedName("signs_count")
    @Expose
    public int signsCount;
    @SerializedName("publish_at")
    @Expose
    public String publishAt;
    @SerializedName("close_at")
    @Expose
    public String closeAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("is_keep_updated")
    @Expose
    public boolean isKeepUpdated;
    @SerializedName("signed")
    @Expose
    public boolean isSigned;

    @SerializedName("updates")
    @Expose
    public ArrayList<Update> updates;

    public Level getLevel() {
        return Level.valueOf(level);
    }

    public boolean isClosedPetition() {
        return !type.equals("live");
    }

    public boolean isSigned() {
        return isSigned;
    }

}