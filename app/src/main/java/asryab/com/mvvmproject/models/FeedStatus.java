package asryab.com.mvvmproject.models;

public enum FeedStatus {
    OPENED,
    CLOSED;

    public String toStringValue() {
        return name().toLowerCase();
    }
}
