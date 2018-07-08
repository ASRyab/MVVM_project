package asryab.com.mvvmproject.models;

public enum ListState {
    POLLS, PETITIONS, TOPICS, POINTS;

    public String toStringValue() {
        return name().toLowerCase();
    }
}
