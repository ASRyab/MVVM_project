package asryab.com.mvvmproject.models.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import asryab.com.mvvmproject.models.Model;
import asryab.com.mvvmproject.models.Profile;

public class User implements Model, Serializable {

    @SerializedName("id")
    public long id;
    @SerializedName("email")
    public String email;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("profile")
    public Profile profile;

    @SerializedName("authorization_token")
    public String token;
    @SerializedName("status")
    public int status;
    @SerializedName("sign_in_count")
    public int signInCount;

    @SerializedName("facebook_id")
    public String facebookId;
    @SerializedName("twitter_id")
    public String twitterId;
    @SerializedName("google_id")
    public String googleId;


    @SerializedName("last_sign_in_at")
    public String lastSignInAt;
    @SerializedName("locked_at")
    public String lockedAt;
    @SerializedName("otp")
    public String otp;
    @SerializedName("otp_sent_at")
    public String otpSentAt;
    @SerializedName("remember")
    public String remember;
    @SerializedName("reset_send_at")
    public String resetSendAt;
    @SerializedName("reset_token")
    public String resetToken;
    @SerializedName("failed_attempts")
    public int failedAttempts;
    @SerializedName("unconfirmed_email")
    public String unconfirmedEmail;
    @SerializedName("confirmation_sent_at")
    public String confirmationSentAt;
    @SerializedName("confirmed_at")
    public String confirmedAt;
    @SerializedName("current_sign_at")
    public String currentSignAt;
    @SerializedName("current_sign_in_ip")
    public String currentSignInIp;
    @SerializedName("unlock_token")
    public String unlockToken;
    @SerializedName("verified")
    public String verified;

}
