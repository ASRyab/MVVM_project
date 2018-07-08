package asryab.com.mvvmproject.models;

public final class SuccessResponse {

    public String success;

    public boolean isCreated() {
        return success != null && success.toLowerCase().equals("created");
    }

}
