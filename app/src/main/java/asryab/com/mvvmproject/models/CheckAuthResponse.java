package asryab.com.mvvmproject.models;

public class CheckAuthResponse implements Model {

    private int userId;
    private String email;

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
